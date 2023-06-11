package ejercicios.ejercicio1;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import _datos.DatosEjercicio1;
import _datos.DatosEjercicio1.Tipo;
import _datos.DatosEjercicio1.Variedad;
import us.lsi.common.List2;
import us.lsi.graphs.virtual.VirtualVertex;

public record Ejercicio1Vertex(Integer index, List<Integer> remaining)
implements VirtualVertex<Ejercicio1Vertex, Ejercicio1Edge, Integer> {

	public static Ejercicio1Vertex of(Integer i, List<Integer> rest) {
		return new Ejercicio1Vertex(i, rest);
	}

	// TODO Consulte las clases GraphsPI5 y TestPI5 

	@Override
	public List<Integer> actions() { //cuanto cojo en cada caso
		// TODO Alternativas de un vertice
		List<Integer> res = List2.empty();

		//si he llegado al final
		if(this.index==DatosEjercicio1.getVariedades()) {
			return res;
		}else if(this.remaining.stream().allMatch(x -> x==0)) {//si me quedo sin café
			res.add(0);
			return res;
		}else { //en caso contrario, selecciono el número de kilos disponibles
			Variedad v = DatosEjercicio1.variedades.get(this.index); //variedad actual
			Integer disponible = cantidadDisponible(v);
			res =  IntStream.rangeClosed(0, disponible) //creo un stream de enteros desde 0 a disponible (Incluidos)
					.boxed() //devuelvo un stream a partir de esto
					.toList(); //lo paso a lista
		}
		return res;

	}

	@Override
	public Ejercicio1Vertex neighbor(Integer a) {
		// TODO Vertice siguiente al actual segun la alternativa a 
		List<Integer> remaining2 = List2.copy(this.remaining);
		remaining2 = actualizaRemaining(a);
		return new Ejercicio1Vertex(index + 1, remaining2); //actualizarlo
	}

	@Override
	public Ejercicio1Edge edge(Integer a) {
		return Ejercicio1Edge.of(this, neighbor(a), a); //unir vertice con su vecino
	}

	// Se explica en practicas.
	public Ejercicio1Edge greedyEdge() {
		if (existeMayorMejor()) {
			return edge(0);
		} else {
			Variedad variedadActual = DatosEjercicio1.variedades.get(index);
			int kg = cantidadDisponible(variedadActual);
			return edge(kg);
		}
	}

	private Boolean existeMayorMejor() {
		Variedad variedadBenMax = IntStream.range(this.index + 1, DatosEjercicio1.getVariedades())
				.boxed()
				.map(i -> DatosEjercicio1.variedades.get(i))
				.sorted(Comparator.comparingDouble(Variedad::beneficio).reversed())
				//.filter(x -> cumpleCondicion(x, this.remaining))
				.findFirst().orElse(null);
		if(variedadBenMax!=null) return resuelveProblema(variedadBenMax) && variedadBenMax.beneficio() > DatosEjercicio1.getBeneficio(index);
		else return false;
	}


	@Override
	public String toString() {
		return "Ejercicio1Vertex [index=" + index + ", remaining=" + remaining + "]";
	}

	public static Ejercicio1Vertex initial() {
		// TODO Auto-generated method stub
		return new Ejercicio1Vertex(0, DatosEjercicio1.tipos.stream().map(Tipo::kgdisponibles).collect(Collectors.toList()));
	}

	public static Predicate<Ejercicio1Vertex> goal() {
		// TODO Auto-generated method stub
		Predicate<Ejercicio1Vertex> pred =  p -> p.index() == DatosEjercicio1.getVariedades(); //recorro las variedades
		return pred;
	}

	public static Predicate<Ejercicio1Vertex> goalHasSolution() {
		// TODO Auto-generated method stub
		Predicate<Ejercicio1Vertex> pred =  p -> p.remaining().stream().allMatch(x -> x>= 0); //tiene solucion si no me he pasado cogiendo kilos
		return pred;
	}

	public static Boolean cumpleCondicion(Variedad variedadActual, List<Integer> remai) {
		Map<String, Double> porcentaje = variedadActual.porcentaje();
		Boolean res = true;

		for (Map.Entry<String, Double> entry : porcentaje.entrySet()) {
			String tipo = entry.getKey();
			Tipo tipoNombre = DatosEjercicio1.tipos.stream().filter(x -> x.Nombre_Tipo().equals(tipo)).findFirst().get();
			Integer i = DatosEjercicio1.tipos.indexOf(tipoNombre); //indice del tipo en el que estoy

			Double porcentajeCojo = entry.getValue();
			if(remai.get(i)<porcentajeCojo) return false;
		}

		return res;
	}

	public Integer cantidadDisponible(Variedad v) {
		List<Integer> disponibles = List2.empty();	//aqui almaceno los Kg que tengo disponibles para cada tipo
		for (Map.Entry<String, Double> entry : v.porcentaje().entrySet()) {
			String tipo = entry.getKey();
			Tipo tipoNombre = DatosEjercicio1.tipos.stream().filter(x -> x.Nombre_Tipo().equals(tipo)).findFirst().get();
			Integer i = DatosEjercicio1.tipos.indexOf(tipoNombre); //indice del tipo en el que estoy

			Double porcentajeCojo = entry.getValue();
			Integer disponible = 0;
			while(this.remaining.get(i)>=(porcentajeCojo*(disponible + 1))) {
				disponible++;
			}
			disponibles.add(disponible);	//añado la cantidad disponible para este tipo

		}

		Integer disponible = disponibles.stream().min(Integer::compare).get();	//me quedo con la menor de ellas... esta será la mayor cantidad
		
		return disponible;
	}

	public Boolean resuelveProblema(Variedad v) {
		Integer cantidadDisp = cantidadDisponible(v);
		List<Integer> remain = actualizaRemaining( cantidadDisp);
		return remain.stream().allMatch(x -> x == 0);
	}

	public List<Integer> actualizaRemaining(Integer a){
		List<Integer> remaining2 = List2.copy(this.remaining);
		Variedad variedadActual = DatosEjercicio1.variedades.get(this.index);
		Map<String, Double> porcentaje = variedadActual.porcentaje();

		for (Map.Entry<String, Double> entry : porcentaje.entrySet()) {
			String tipo = entry.getKey();
			Double porcentajeCojo = entry.getValue();
			Tipo tipoNombre = DatosEjercicio1.tipos.stream().filter(x -> x.Nombre_Tipo().equals(tipo)).findFirst().get();
			Integer i = DatosEjercicio1.tipos.indexOf(tipoNombre); //indice del tipo en el que estoy
			Integer cantidadActual = remaining2.get(i);
			Integer cojo = (int) Math.floor(a*porcentajeCojo);//actualizo remaining
			remaining2.set(i, cantidadActual-cojo);
		}
		return remaining2;
	}


}
