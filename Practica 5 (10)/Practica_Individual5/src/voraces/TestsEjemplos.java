package voraces;

import java.util.List;
import java.util.function.Predicate;

import _datos.DatosEjercicio1;
import _datos.DatosEjercicio2;
import _datos.DatosEjercicio3;
import _datos.DatosEjercicio4;
import _soluciones.SolucionEjercicio1;
import _soluciones.SolucionEjercicio2;
import _soluciones.SolucionEjercicio3;
import _soluciones.SolucionEjercicio4;
import ejercicios.ejercicio1.Ejercicio1Vertex;
import ejercicios.ejercicio2.Ejercicio2Vertex;
import ejercicios.ejercicio3.Ejercicio3Vertex;
import ejercicios.ejercicio4.Ejercicio4Vertex;
import us.lsi.common.List2;
import us.lsi.common.String2;

// Voraces de forma manual para los ejemplos. Soluciones iterativas y funcionales
public class TestsEjemplos {

	public static void main(String[] args) {
//		testVorazE1();
		
//		testVorazE2_Fichero1();
//		testVorazE2_Fichero2();
//		testVorazE2_Fichero3();
			
		testVorazE4();
		
	}

	// Ejemplo1: Voraz Iterativo Manual. Iterando sobre vertices
	private static void testVorazE1() {
		List.of(1,2,3).forEach(num_test -> {
			DatosEjercicio1.iniDatos("ficheros/Ejercicio1DatosEntrada"+num_test+".txt");

			List<Integer> path = List2.empty();
			
			Ejercicio1Vertex v = Ejercicio1Vertex.initial();
			Predicate<Ejercicio1Vertex> last = Ejercicio1Vertex.goal();
			Predicate<Ejercicio1Vertex> solution = Ejercicio1Vertex.goalHasSolution();
			while(!solution.test(v) && !last.test(v)) {
				var e = v.greedyEdge();
				path.add(e.action());
				v = e.target();
			}
			String2.toConsole("Voraz Manual: %s\n%s",
			SolucionEjercicio1.of_Range(path),String2.linea());
		});		
	}
	

	
	private static void testVorazE2_Fichero1() {
			DatosEjercicio2.iniDatos("ficheros/Ejercicio2DatosEntrada1.txt");
			List<Integer> path = List2.empty();
			
			Ejercicio2Vertex v = Ejercicio2Vertex.initial();
			Predicate<Ejercicio2Vertex> last = Ejercicio2Vertex.goal();
			Predicate<Ejercicio2Vertex> solution = Ejercicio2Vertex.goalHasSolution();
			while(!solution.test(v) && !last.test(v)) {
				var e = v.greedyEdge();
				path.add(e.action());
				v = e.target();
			}
			String2.toConsole("Voraz Manual: %s\n%s",
			SolucionEjercicio2.of_Range(path),String2.linea());
	}
	
	private static void testVorazE2_Fichero2() {
		DatosEjercicio2.iniDatos("ficheros/Ejercicio2DatosEntrada2.txt");
		List<Integer> path = List2.empty();

		Ejercicio2Vertex v = Ejercicio2Vertex.initial();
		Predicate<Ejercicio2Vertex> last = Ejercicio2Vertex.goal();
		Predicate<Ejercicio2Vertex> solution = Ejercicio2Vertex.goalHasSolution();
		while(!solution.test(v) && !last.test(v)) {
			var e = v.greedyEdge();
			path.add(e.action());
			v = e.target();
		}
		String2.toConsole("Voraz Manual: %s\n%s",
				SolucionEjercicio2.of_Range(path),String2.linea());
	}

	private static void testVorazE2_Fichero3() {
		DatosEjercicio2.iniDatos("ficheros/Ejercicio2DatosEntrada3.txt");
		List<Integer> path = List2.empty();

		Ejercicio2Vertex v = Ejercicio2Vertex.initial();
		Predicate<Ejercicio2Vertex> last = Ejercicio2Vertex.goal();
		Predicate<Ejercicio2Vertex> solution = Ejercicio2Vertex.goalHasSolution();
		while(!solution.test(v) && !last.test(v)) {
			var e = v.greedyEdge();
			path.add(e.action());
			v = e.target();
		}
		String2.toConsole("Voraz Manual: %s\n%s",
				SolucionEjercicio2.of_Range(path),String2.linea());
	}
	
	
	private static void testVorazE4() {
		List.of(1,2).forEach(num_test -> {
			DatosEjercicio4.iniDatos("ficheros/Ejercicio4DatosEntrada"+num_test+".txt");

			List<Integer> path = List2.empty();
			
			Ejercicio4Vertex v = Ejercicio4Vertex.initial();
			Predicate<Ejercicio4Vertex> last = Ejercicio4Vertex.goal();
			Predicate<Ejercicio4Vertex> solution = Ejercicio4Vertex.goalHasSolution();
			while(!solution.test(v) && !last.test(v)) {
				var e = v.greedyEdge();
				path.add(e.action());
				v = e.target();
			}
			String2.toConsole("Voraz Manual: %s\n%s",
			SolucionEjercicio4.of_Range(path),String2.linea());
		});		
	}
}
