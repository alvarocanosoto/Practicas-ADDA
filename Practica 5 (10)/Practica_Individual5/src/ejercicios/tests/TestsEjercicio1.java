package ejercicios.tests;

import java.util.List;
import java.util.function.Predicate;

import _datos.DatosEjercicio1;
import _soluciones.SolucionEjercicio1;
import _utils.GraphsPI5;
import _utils.TestsPI5;
import ejercicios.ejercicio1.Ejercicio1Vertex;

public class TestsEjercicio1 {

	public static void main(String[] args) {
		List.of(1,2,3).forEach(num_test -> {
			TestsPI5.iniTest("Ejercicio1DatosEntrada", num_test, DatosEjercicio1::iniDatos);

			// TODO Defina un m. factoria para el vertice inicial
			Ejercicio1Vertex v_inicial = Ejercicio1Vertex.initial();
			// TODO Defina un m. static para los vertices finales 
			Predicate<Ejercicio1Vertex> es_terminal = Ejercicio1Vertex.goal();

			var gp = TestsPI5.testGreedy(GraphsPI5.greedyEjercicio1Graph(v_inicial, es_terminal));
			TestsPI5.toConsole("Voraz", gp, SolucionEjercicio1::of);

			var path = TestsPI5.testAStar(GraphsPI5.Ejercicio1Graph(v_inicial, es_terminal), gp);
			TestsPI5.toConsole("A*", path, SolucionEjercicio1::of);

			path = TestsPI5.testPDR(GraphsPI5.Ejercicio1Graph(v_inicial, es_terminal), gp);
			TestsPI5.toConsole("PDR", path, SolucionEjercicio1::of);

			path = TestsPI5.testBT(GraphsPI5.Ejercicio1Graph(v_inicial, es_terminal), gp);
			TestsPI5.toConsole("BT", path, SolucionEjercicio1::of);

			TestsPI5.line("*");

		});
	}

}
