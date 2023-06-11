package ejercicios.tests;

import java.util.List;
import java.util.function.Predicate;

import _datos.DatosEjercicio4;
import _soluciones.SolucionEjercicio4;
import _utils.GraphsPI5;
import _utils.TestsPI5;
import ejercicios.ejercicio4.Ejercicio4Vertex;

//Clase para todos los tests del ejemplo 3 mediante Greedy, A*, PDR y BT 
public class TestsEjercicio4 {


	public static void main(String[] args) {
		List.of(1,2).forEach(num_test -> {
			TestsPI5.iniTest("Ejercicio4DatosEntrada", num_test, DatosEjercicio4::iniDatos);

			// TODO Defina un m. factoria para el vertice inicial
			Ejercicio4Vertex v_inicial = Ejercicio4Vertex.initial();
			// TODO Defina un m. static para los vertices finales 
			Predicate<Ejercicio4Vertex> es_terminal = Ejercicio4Vertex.goal();

			var gp = TestsPI5.testGreedy(GraphsPI5.greedyEjercicio4Graph(v_inicial, es_terminal));
			TestsPI5.toConsole("Voraz", gp, SolucionEjercicio4::of);

			var path = TestsPI5.testAStar(GraphsPI5.Ejercicio4Graph(v_inicial, es_terminal), gp);
			TestsPI5.toConsole("A*", path, SolucionEjercicio4::of);

			path = TestsPI5.testPDR(GraphsPI5.Ejercicio4Graph(v_inicial, es_terminal), gp);
			TestsPI5.toConsole("PDR", path, SolucionEjercicio4::of);

			path = TestsPI5.testBT(GraphsPI5.Ejercicio4Graph(v_inicial, es_terminal), gp);
			TestsPI5.toConsole("BT", path, SolucionEjercicio4::of);

			TestsPI5.line("*");
		});
	}


}
