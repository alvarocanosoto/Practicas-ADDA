package ejercicios.ejercicio3.manual;


import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import _datos.DatosEjercicio3;
import _datos.DatosEjercicio3.Investigador;
import _datos.DatosEjercicio3.Trabajo;
import us.lsi.common.List2;

public record Ejercicio3Problem(Integer index, List<Integer> remainingInv, List<List<Integer>> remainingEsp)
{

	public static Ejercicio3Problem of(Integer i, List<Integer> rest, List<List<Integer>> esp) {
		return new Ejercicio3Problem(i, rest, esp);
	}

	// TODO Consulte las clases GraphsPI5 y TestPI5 
	public static Predicate<Ejercicio3Problem> goal(){
		return p -> p.index() == DatosEjercicio3.getTrabajos() * DatosEjercicio3.getInvestigadores(); 
	}

	public static Predicate<Ejercicio3Problem> goalHasSolution(){
		return p -> p.remainingInv.stream().allMatch(x -> x>=0);
	}

	public static Ejercicio3Problem initial() {
		List<Integer> listaCapacidades = DatosEjercicio3.investigadores.stream().map(Investigador::capacidad).toList();
		List<List<Integer>> listaEspecialidades = DatosEjercicio3.trabajos.stream().map(Trabajo::reparto).map(x -> x.values().stream().toList()).toList();
		return of(0,listaCapacidades, listaEspecialidades);
	}


	@Override
	public String toString() {
		return "Ejercicio3Problem [index=" + index + ", remainingInv=" + remainingInv + ", remainingEsp=" + remainingEsp
				+ "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(index, remainingEsp, remainingInv);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ejercicio3Problem other = (Ejercicio3Problem) obj;
		return Objects.equals(index, other.index) && Objects.equals(remainingEsp, other.remainingEsp)
				&& Objects.equals(remainingInv, other.remainingInv);
	}

	public List<Integer> actions() {
		// TODO Alternativas de un vertice .
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


	public Ejercicio3Problem neighbor(Integer a) {
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

		return of(index + 1, remainingInv2, remainingEsp2);
	}

	public Double heuristic() {
		Double res = 0.;
		List<Integer> remainingHoras = remainingInv(); //lista de horas disponibles para cada trabajador
		List<Integer> remainingEspecialidades = List2.ofTam(0, DatosEjercicio3.getEspecialidades()); //lista de horas disponibles para cada especialidad
		List<List<Integer>> remainingTotal = remainingEsp(); //Lista de lo que falta para completar cada trabajo
		for(int i=0; i < remainingHoras.size();i++) {
			Integer especialidad = DatosEjercicio3.investigadores.get(i).especialidad();
			Integer horasAhora = remainingEspecialidades.get(especialidad) + remainingHoras.get(i);
			remainingEspecialidades.set(especialidad, horasAhora ); //actualizo el tiempo disponible para cada especialidad
		}
		
		for(List<Integer> l : remainingTotal) {
			if(termina(remainingEspecialidades, l)) {
				res += DatosEjercicio3.trabajos.get(remainingTotal.indexOf(l)).calidad();
			}
		}
		return res;
	}

	public Integer calidadActual() {
		Integer res = IntStream.range(0, this.remainingEsp.size())
				.boxed()
				.filter(x -> this.remainingEsp.get(x).stream().allMatch(y -> y == 0))
				.map(x -> DatosEjercicio3.trabajos.get(x).calidad())
				.reduce((a,b) -> a = a+b).orElse(0);
		return res;
	}


	public Integer indiceTrabajoActual() {
		Integer m = DatosEjercicio3.getTrabajos();
		return this.index%m;
	}
	
	public Integer indiceInvActual() {
		Integer m = DatosEjercicio3.getTrabajos();
		return this.index/m;
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
	
	public static Boolean termina(List<Integer> rE,List<Integer> l ) {
		Boolean res = true;
		for(int i = 0; i < rE.size(); i++) {
			if(l.get(i)-rE.get(i) > 0) res = false;
		}
		return res;
	}
}
