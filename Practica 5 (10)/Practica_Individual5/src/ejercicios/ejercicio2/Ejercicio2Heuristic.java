package ejercicios.ejercicio2;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import _datos.DatosEjercicio2;
import _datos.DatosEjercicio2.Curso;
import us.lsi.common.List2;
import us.lsi.common.Set2;

public class Ejercicio2Heuristic {
	
	// Se explica en practicas.
	public static Double heuristic(Ejercicio2Vertex v1, Predicate<Ejercicio2Vertex> goal, Ejercicio2Vertex v2) {		
//		//if(cubro alguna tematica)
//			//me quedo con la de menos coste
//		//else return Double.MAXVALUE
		return v1.remainingTem().isEmpty()? 0.:
			IntStream.range(v1.index(), DatosEjercicio2.getCursos())
					.filter(i -> cubre(i, v1)!=0)
					.mapToDouble(i -> DatosEjercicio2.getPrecioInscripcion(i))
					.min()
					.orElse(Double.MAX_VALUE);
	}	
	
	public static Integer cubre(Integer i, Ejercicio2Vertex v1) {
		Curso cursoActual = DatosEjercicio2.cursos.get(i);
		Set<Integer> remainingTem2 = Set2.difference(v1.remainingTem(),cursoActual.tematicas()); //tematicas actualizadas
		if(remainingTem2.equals(v1.remainingTem())) return 0; //si no cubro ninguna devuelvo 0
		else if(remainingTem2.isEmpty()) return 1; //si cubro todas devuelvo 1
		else return 2; //si cubro pero no son todas, devuelvo 2
	}

}

