package ejercicios.ejercicio3;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import _datos.DatosEjercicio3;
import _datos.DatosEjercicio3.Investigador;
import _datos.DatosEjercicio3.Trabajo;
import us.lsi.common.List2;
import us.lsi.graphs.virtual.VirtualVertex;

// Uso el segundo modelo
public record Ejercicio3Vertex(Integer index, List<Integer> remainingInv, List<List<Integer>> remainingEsp) 
     implements VirtualVertex<Ejercicio3Vertex,Ejercicio3Edge,Integer> {

	public static Ejercicio3Vertex of(Integer i, List<Integer> rest, List<List<Integer>> esp) {
		return new Ejercicio3Vertex(i, rest, esp);
	}
	
	@Override
	public List<Integer> actions() {
		// TODO Alternativas de un vertice 
		List<Integer> res = List2.empty();
		Integer m = DatosEjercicio3.getTrabajos();
		Integer n = DatosEjercicio3.getInvestigadores();
		Integer indiceTrabajoActual = indiceTrabajoActual();
		Integer indiceInvActual = indiceInvActual();

		if(this.index==(n * m)) { //si estoy en el último vértice
			return res;
		}else if(this.remainingInv.get(indiceInvActual) == 0) { //si no tengo más días disponibles
			res.add(0);
			return res;
		}else {
			//cuántos movimientos puedo hacer? Dependo de la capacidad que le qude al investigador actual y de
			//los días que falten para cubrir la especialidad que trata ese investigador
			res.add(0);
			Investigador investigadorActual = DatosEjercicio3.investigadores.get(indiceInvActual);
			Integer especialidadActual = investigadorActual.especialidad(); 

			List<Integer> remainingEspecialidades = disponibleEspecialidad();

			List<Integer> listaTrabajos = this.remainingEsp.get(indiceTrabajoActual);

			Integer capacidadDisp = this.remainingInv.get(indiceInvActual);
			Integer capacidadDisp2 = remainingEspecialidades.get(especialidadActual);
			Integer capacidadNecesito = listaTrabajos.get(especialidadActual);
			Integer resultado = Math.min(Math.min(capacidadDisp, capacidadDisp2), capacidadNecesito);
			List<Integer> listaRes = IntStream.rangeClosed(1, resultado).boxed().toList();

			res.addAll(listaRes);
		}

		return res;	
	}

	@Override
	public Ejercicio3Vertex neighbor(Integer a) {
		// TODO Vertice siguiente al actual segun la alternativa a 
		List<Integer> remainingInv2 = List2.copy(remainingInv);
		List<List<Integer>> remainingEsp2 = List2.copy(remainingEsp);
		
		Integer indiceTrabajoActual = indiceTrabajoActual();
		Integer indiceInvActual = indiceInvActual();
		Investigador investigadorActual = DatosEjercicio3.investigadores.get(indiceInvActual);
		
		//cuando creo un vecino tengo que actualizar la lista de días disponibles de los investigadores
		Integer capacidadInvestigador = this.remainingInv.get(indiceInvActual);
		remainingInv2.set(indiceInvActual, capacidadInvestigador-a);
		//cuando creo un vecino tengo que actualizar los días que quedan para completar el trabajo	
		Integer diasQuedan = remainingEsp.get(indiceTrabajoActual).get(investigadorActual.especialidad());

		List<Integer> lista_aux = List2.setElement(remainingEsp2.get(indiceTrabajoActual), investigadorActual.especialidad(), diasQuedan - a);
		remainingEsp2.set(indiceTrabajoActual, lista_aux);

		return new Ejercicio3Vertex(index + 1, remainingInv2, remainingEsp2);
	}

	@Override
	public Ejercicio3Edge edge(Integer a) {
		return Ejercicio3Edge.of(this,this.neighbor(a),a);
	}
	
	// Se explica en practicas.
	public Ejercicio3Edge greedyEdge() {
		Investigador investigadorActual = DatosEjercicio3.investigadores.get(indiceInvActual());
		List<Integer> remainingEspecialidades = disponibleEspecialidad();
		Integer especialidadActual = investigadorActual.especialidad(); 
		List<Integer> listaTrabajos = this.remainingEsp.get(indiceTrabajoActual());

		Integer capacidadDisp = this.remainingInv.get(indiceInvActual());
		Integer capacidadDisp2 = remainingEspecialidades.get(especialidadActual);
		Integer capacidadNecesito = listaTrabajos.get(especialidadActual);
		Integer resultado = Math.min(Math.min(capacidadDisp, capacidadDisp2), capacidadNecesito);

		if(termina(disponibleEspecialidad(), indiceTrabajoActual())) {
			return edge(resultado);
		}else {
			Trabajo trabajoTerminaMasCalidad = IntStream.range(indiceInvActual()+1, DatosEjercicio3.getTrabajos())
					.boxed()
					.filter(x -> termina(disponibleEspecialidad(), x))
					.map(i -> DatosEjercicio3.trabajos.get(i))
					.max(Comparator.comparingInt(Trabajo::calidad)).orElse(null);
			
			if(trabajoTerminaMasCalidad==null) return edge(resultado);
			else return edge(0);
		}		
	}

	
	public static Ejercicio3Vertex initial() {
		// TODO Auto-generated method stub
		List<Integer> listaCapacidades = DatosEjercicio3.investigadores.stream().map(Investigador::capacidad).toList();
		List<List<Integer>> listaEspecialidades = DatosEjercicio3.trabajos.stream().map(Trabajo::reparto).map(x -> x.values().stream().toList()).toList();
		return new Ejercicio3Vertex(0,listaCapacidades, listaEspecialidades);
	}

	public static Predicate<Ejercicio3Vertex> goal() {
		// TODO Auto-generated method stub
		Predicate<Ejercicio3Vertex> pred =  p -> p.index() == DatosEjercicio3.getTrabajos() * DatosEjercicio3.getInvestigadores(); //n * m
		return pred;
	}
	
	public static Predicate<Ejercicio3Vertex> goalHasSolution() {
		// TODO Auto-generated method stub
		Predicate<Ejercicio3Vertex> pred =  p -> p.remainingInv.stream().allMatch(x -> x>=0);
		return pred;
	}
	
	public Integer indiceTrabajoActual() {
		Integer m = DatosEjercicio3.getTrabajos();
		return this.index%m;
	}
	
	public Integer indiceInvActual() {
		Integer m = DatosEjercicio3.getTrabajos();
		return this.index/m;
	}
	
	
	public Boolean termina(List<Integer> rE, Integer t) {
		Boolean res = true;
		List<Integer> remTrabajoI = this.remainingEsp.get(t); //lista de horas que faltan para terminar el trabajo actual
		
		for(int i = 0; i < rE.size(); i++) {
			if(remTrabajoI.get(i)-rE.get(i) > 0) res = false; //si le resto lo que me queda disponible y no lo acabo, no se termina
		}
		return res;
	}
	
	public List<Integer> disponibleEspecialidad(){		
		List<Integer> remainingEspecialidades = List2.ofTam(0, DatosEjercicio3.getEspecialidades()); //lista de horas disponibles para cada especialidad
		for(int i=0; i < this.remainingInv.size();i++) {
			Integer especialidad = DatosEjercicio3.investigadores.get(i).especialidad();
			Integer horasAhora = remainingEspecialidades.get(especialidad) + this.remainingInv.get(i);
			remainingEspecialidades.set(especialidad, horasAhora );//actualizo el tiempo disponible para cada especialidad
		}
		return remainingEspecialidades;
	}
	
	public Integer maximoCojo(Integer indiceTrabajoActual) {
		Integer res = 0;
		List<Integer> remainingTrabajo = this.remainingEsp.get(indiceTrabajoActual);
		List<Integer> remainingEsp = disponibleEspecialidad();
		for(int i = 0; i < remainingEsp.size(); i++) {
			Integer rexAux = remainingEsp.get(i)-remainingTrabajo.get(i);
			if(rexAux<res) res = rexAux;
		}
		
		return res;
	}

	
}
