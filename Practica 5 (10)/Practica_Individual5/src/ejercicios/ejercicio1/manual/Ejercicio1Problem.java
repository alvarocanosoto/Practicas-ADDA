package ejercicios.ejercicio1.manual;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import _datos.DatosEjercicio1;
import _datos.DatosEjercicio1.Tipo;
import _datos.DatosEjercicio1.Variedad;
import us.lsi.common.List2;

public record Ejercicio1Problem(
		Integer index, 
		List<Integer> remaining) {

	public List<Integer> actions() {
		List<Integer> res = List2.empty();
		// Comprobar si estamos en el final.
		if(index == DatosEjercicio1.getVariedades()) {
			return List2.empty();
		}
		
//		Integer numero = DatosEjercicio1.getElemento(index);
//		Integer maximo_veces = (remaining / numero);
		
		if(this.remaining.stream().allMatch(x -> x==0)) {//si me quedo sin café
			return List2.of(0);
		}else { //en caso contrario, selecciono el número de kilos disponibles

			Variedad v = DatosEjercicio1.variedades.get(this.index); //variedad actual
			Integer disponible = cantidadDisponible(v);
			res =  IntStream.rangeClosed(0, disponible) //creo un stream de enteros desde 0 a disponible (Incluidos)
					.boxed() //devuelvo un stream a partir de esto
					.toList(); //lo paso a lista
//			System.out.println("acciones disponibles para la variedad " + v.nombre() + ": " +res);
		}
		return res;
	}

	
	public Ejercicio1Problem neighbor(Integer a) {
		List<Integer> remaining2 = List2.copy(remaining);
		remaining2 = actualizaRemaining(a);
		return new Ejercicio1Problem(index +1 ,remaining2);
	}

	
	public Boolean existeMayorMejor() {
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
		return "Ejercicio1Problem [index=" + index + ", remaining=" + remaining + "]";
	}


	@Override
	public int hashCode() {
		return Objects.hash(index, remaining);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ejercicio1Problem other = (Ejercicio1Problem) obj;
		return Objects.equals(index, other.index) && Objects.equals(remaining, other.remaining);
	}


	public static Ejercicio1Problem initial() {
		return new Ejercicio1Problem(0, DatosEjercicio1.tipos.stream().map(Tipo::kgdisponibles).collect(Collectors.toList()));
	}

	public static Predicate<Ejercicio1Problem> goal() {
		Predicate<Ejercicio1Problem> pred = p -> p.index() == DatosEjercicio1.getVariedades();
		return pred;
	}
	
	public static Predicate<Ejercicio1Problem> goalHasSolution() {
		Predicate<Ejercicio1Problem> pred = p -> p.remaining().stream().allMatch(x -> x>= 0);
		return pred;
	}

	// Estimación optimista del futuro
	public Double heuristic() {		
		Double res = 0.;
		List<Variedad> variedades = DatosEjercicio1.variedades;
		for(Variedad v : variedades) {
			Double resAux = v.beneficio()*cantidadDisponible(v);
			if(resAux>res) res = resAux;
		}
		return res;
		
	}
	
	
	public List<Integer> actualizaRemaining(Integer a){
		List<Integer> remaining2 = List2.copy(remaining);
		Variedad variedadActual = DatosEjercicio1.variedades.get(index);
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
	
	public Boolean resuelveProblema(Variedad v) {
		Integer cantidadDisp = cantidadDisponible(v);
		List<Integer> remain = actualizaRemaining( cantidadDisp);
		return remain.stream().allMatch(x -> x == 0);
	}
	
	public Integer cantidadDisponible(Variedad v) {
		List<Integer> disponibles = List2.empty();	//aqui almaceno los Kg que tengo disponibles para cada tipo
		for (Map.Entry<String, Double> entry : v.porcentaje().entrySet()) {
			String tipo = entry.getKey();
			Tipo tipoNombre = DatosEjercicio1.tipos.stream().filter(x -> x.Nombre_Tipo().equals(tipo)).findFirst().get();
			Integer i = DatosEjercicio1.tipos.indexOf(tipoNombre); //indice del tipo en el que estoy

			Double porcentajeCojo = entry.getValue();
			Integer disponible = 0;
			while(remaining.get(i)>=(porcentajeCojo*(disponible + 1))) {
				disponible++;
			}
			disponibles.add(disponible);	//añado la cantidad disponible para este tipo

		}

		Integer disponible = disponibles.stream().min(Integer::compare).get();	//me quedo con la menor de ellas... esta será la mayor cantidad
		
//		System.out.println("cantidad disponible de la variedad " + v.nombre() + ": "+ disponible);
		return disponible;
	}

}
