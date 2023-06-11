package ejercicios.ejercicio2;


import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import _datos.DatosEjercicio2;
import _datos.DatosEjercicio2.Curso;
import us.lsi.common.List2;
import us.lsi.common.Set2;
import us.lsi.graphs.virtual.VirtualVertex;

public record Ejercicio2Vertex(Integer index, Set<Integer> remainingTem, Set<Integer> selectedCent) 
    implements VirtualVertex<Ejercicio2Vertex, Ejercicio2Edge, Integer>{
	
	public static Ejercicio2Vertex of(Integer i, Set<Integer> rest, Set<Integer> cent) {
		return new Ejercicio2Vertex(i, rest,cent);
	}
	
	// TODO Consulte las clases GraphsPI5 y TestPI5 
	
	@Override
	public List<Integer> actions() {
		// TODO Alternativas de un vertice
		if(goal().test(this)) { //si llego al final devuelvo una lista vacia
			return List2.empty();
		}else if(this.remainingTem().isEmpty()) { //si ya no quedan temáticas por recubrir no hago nada
			return List2.of(0);
		}else {
			Curso cursoActual = DatosEjercicio2.cursos.get(this.index);
			if(this.index == DatosEjercicio2.getCursos()-1) { //si estoy en el último
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
	
	@Override
	public Ejercicio2Vertex neighbor(Integer a) {
		// TODO Vertice siguiente al actual segun la alternativa a
		Set<Integer> remainingTem2 = Set2.copy(this.remainingTem);
		Set<Integer> selectedCent2 = Set2.copy(this.selectedCent);
		Set<Integer> tematicasActuales = DatosEjercicio2.cursos.get(this.index).tematicas();
		if(a!=0) {
			remainingTem2 = Set2.difference(this.remainingTem,tematicasActuales); //remaining.removeAll(tematicas curso actual)
			selectedCent2.add(DatosEjercicio2.cursos.get(this.index).centro()); //añadir centro actual
		}
		return of(index + 1, remainingTem2, selectedCent2);
	}
	
	@Override
	public Ejercicio2Edge edge(Integer a) {
		return Ejercicio2Edge.of(this, neighbor(a), a);
	}
	
	// Se explica en practicas.
	public Ejercicio2Edge greedyEdge() {
		Curso cursoActual = DatosEjercicio2.cursos.get(this.index);
		if(noSuperaCentros(cursoActual)) return cubre()==0? edge(0): edge(1);
		else return edge(0);
	}
	
	@Override
	public String toString() {
		return "Ejercicio2Vertex [index=" + index + ", remainingTem=" + remainingTem + ", selectedCent=" + selectedCent
				+ "]";
	}

	public static Ejercicio2Vertex initial() {
		// TODO Auto-generated method stub
		return new Ejercicio2Vertex(0, Set2.of(DatosEjercicio2.getListaTematicas()), Set2.empty());
	}

	public static Predicate<Ejercicio2Vertex> goal() {
		// TODO Auto-generated method stub
		Predicate<Ejercicio2Vertex> pred =  p -> p.index() == DatosEjercicio2.getCursos();
		return pred;
	}
	
	public static Predicate<Ejercicio2Vertex> goalHasSolution() {
		// TODO Auto-generated method stub
		Predicate<Ejercicio2Vertex> pred =  p -> p.remainingTem().isEmpty() && p.selectedCent().size()<=DatosEjercicio2.maxCentros; //tiene solucion si no quedan temáticas por seleccionar y no se supera el máximo de centros
		return pred;
	}
	
	public Boolean noSuperaCentros(Curso cursoActual) {
		Boolean res1 = this.selectedCent.contains(cursoActual.centro());
		Boolean res2 = this.selectedCent.size() < DatosEjercicio2.maxCentros;
		return res1 || res2;
	}
	

	
	public Integer cubre() {
		Curso cursoActual = DatosEjercicio2.cursos.get(this.index);
		Set<Integer> remainingTem2 = Set2.difference(this.remainingTem,cursoActual.tematicas()); //tematicas actualizadas
		if(remainingTem2.equals(this.remainingTem)) return 0; //si no cubro ninguna devuelvo 0
		else if(remainingTem2.isEmpty()) return 1; //si cubro todas devuelvo 1
		else return 2; //si cubro pero no son todas, devuelvo 2
	}
}
