package ejercicios.ejercicio2.manual;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


import _datos.DatosEjercicio2;
import _datos.DatosEjercicio2.Curso;
import ejercicios.ejercicio2.Ejercicio2Heuristic;
import ejercicios.ejercicio2.Ejercicio2Vertex;
import us.lsi.common.List2;
import us.lsi.common.Set2;

public record Ejercicio2Problem(
		Integer index, 
		Set<Integer> remainingTem, Set<Integer> selectedCent) {

	public List<Integer> actions() {
		if(goal().test(this)) { //si llego al final devuelvo una lista vacia
			return List2.empty();
		}else if(remainingTem.isEmpty()) { //si ya no quedan temáticas por recubrir no hago nada
			return List2.of(0);
		}else {
			Curso cursoActual = DatosEjercicio2.cursos.get(index);
			if(index == DatosEjercicio2.getCursos()-1) { //si estoy en el último
				return noSuperaCentros(cursoActual) && cubre()==1? List2.of(1): //si termino el problema devuelvo 1
																   List2.of(0); //si voy a dar una solucion incorrecta o no lo termino no hago nada
			}else if(cubre()==0) { //si no cubro ninguna tematica, no hago nada
				return List2.of(0);
			}else { //si aporto algo, pero no lo termino
				if(noSuperaCentros(cursoActual)){ //si la solucion cumple las restricciones
					return List2.of(0,1);
				}else {	//si la solucion NO cumple las restricciones
					return List2.of(0);
				}
			}
		}
	}

	
	public Ejercicio2Problem neighbor(Integer a) {
		Set<Integer> remainingTem2 = Set2.copy(this.remainingTem);
		Set<Integer> selectedCent2 = Set2.copy(this.selectedCent);
		Set<Integer> tematicasActuales = DatosEjercicio2.cursos.get(this.index).tematicas();
		if(a!=0) {
			remainingTem2 = Set2.difference(this.remainingTem,tematicasActuales); //remaining.removeAll(tematicas curso actual)
			selectedCent2.add(DatosEjercicio2.cursos.get(this.index).centro()); //añadir centro actual
		}
		return new Ejercicio2Problem(index + 1, remainingTem2, selectedCent2);
	}


	@Override
	public String toString() {
		return "Ejercicio2Problem [index=" + index + ", remainingTem=" + remainingTem + ", selectedCent=" + selectedCent
				+ "]";
	}


	@Override
	public int hashCode() {
		return Objects.hash(index, remainingTem, selectedCent);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ejercicio2Problem other = (Ejercicio2Problem) obj;
		return Objects.equals(index, other.index) && Objects.equals(remainingTem, other.remainingTem)
				&& Objects.equals(selectedCent, other.selectedCent);
	}


	public static Ejercicio2Problem initial() {
		return new Ejercicio2Problem(0, Set2.of(DatosEjercicio2.getListaTematicas()), Set2.empty());
	}

	public static Predicate<Ejercicio2Problem> goal() {
		Predicate<Ejercicio2Problem> pred = p -> p.index() == DatosEjercicio2.getCursos();
		return pred;
	}
	
	public static Predicate<Ejercicio2Problem> goalHasSolution() {
		Predicate<Ejercicio2Problem> pred = p -> p.remainingTem().isEmpty() && p.selectedCent().size()<=DatosEjercicio2.maxCentros;
		return pred;
	}

	// Estimación optimista del futuro
	public Double heuristic() {		
		return remainingTem.isEmpty()? 0.:
			IntStream.range(index, DatosEjercicio2.getCursos())
					.filter(i -> cubre(i)!=0)
					.mapToDouble(i -> DatosEjercicio2.getPrecioInscripcion(i))
					.min()
					.orElse(Double.MAX_VALUE);
	}
	

	public Boolean noSuperaCentros(Curso cursoActual) {
		Boolean res1 = selectedCent.contains(cursoActual.centro());
		Boolean res2 = selectedCent.size()+1 <= DatosEjercicio2.maxCentros;
		return res1 || res2;
	}
	
	public Integer cubre() {
		Curso cursoActual = DatosEjercicio2.cursos.get(index);
		Set<Integer> remainingTem2 = Set2.difference(remainingTem,cursoActual.tematicas()); //tematicas actualizadas
		if(remainingTem2.equals(remainingTem)) return 0; //si no cubro ninguna devuelvo 0
		else if(remainingTem2.isEmpty()) return 1; //si cubro todas devuelvo 1
		else return 2; //si cubro pero no son todas, devuelvo 2
	}
	
	public Integer cubre(Integer i) {
		Curso cursoActual = DatosEjercicio2.cursos.get(i);
		Set<Integer> remainingTem2 = Set2.difference(remainingTem,cursoActual.tematicas()); //tematicas actualizadas
		if(remainingTem2.equals(remainingTem)) return 0; //si no cubro ninguna devuelvo 0
		else if(remainingTem2.isEmpty()) return 1; //si cubro todas devuelvo 1
		else return 2; //si cubro pero no son todas, devuelvo 2
	}
}
