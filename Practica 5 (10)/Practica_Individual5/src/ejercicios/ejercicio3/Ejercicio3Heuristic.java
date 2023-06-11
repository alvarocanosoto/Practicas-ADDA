package ejercicios.ejercicio3;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import _datos.DatosEjercicio3;
import us.lsi.common.List2;

public class Ejercicio3Heuristic {
	
	// Se explica en practicas.
	public static Double heuristic(Ejercicio3Vertex v1, Predicate<Ejercicio3Vertex> goal, Ejercicio3Vertex v2) {
		Double res = 0.;
		List<Integer> remainingHoras = v1.remainingInv(); //lista de horas disponibles para cada trabajador
		List<Integer> remainingEspecialidades = List2.ofTam(0, DatosEjercicio3.getEspecialidades()); //lista de horas disponibles para cada especialidad
		List<List<Integer>> remainingTotal = v1.remainingEsp(); //Lista de lo que falta para completar cada trabajo
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
		
		//compruebo cuantos trabajos puedo terminar (con remainingInv y remainingEsp) sin restar las horas que trabajan y sumo la calidad de los trabajos completables
		
		
	}
	
	public static Boolean termina(List<Integer> rE,List<Integer> l ) {
		Boolean res = true;
		for(int i = 0; i < rE.size(); i++) {
			if(l.get(i)-rE.get(i) > 0) res = false;
		}
		return res;
	}
	
}
