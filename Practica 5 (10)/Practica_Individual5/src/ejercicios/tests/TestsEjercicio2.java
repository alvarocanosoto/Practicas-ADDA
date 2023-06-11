package ejercicios.tests;

import java.util.List;
import java.util.function.Predicate;

import _datos.DatosEjercicio2;
import _soluciones.SolucionEjercicio2;
import _utils.GraphsPI5;
import _utils.TestsPI5;
import ejercicios.ejercicio2.Ejercicio2Vertex;

public class TestsEjercicio2 {

	public static void main(String[] args) {
		List.of(3).forEach(num_test -> {
			TestsPI5.iniTest("Ejercicio2DatosEntrada", num_test, DatosEjercicio2::iniDatos);
			
			// TODO Defina un m. factoria para el vertice inicial
			Ejercicio2Vertex v_inicial = Ejercicio2Vertex.initial();
			// TODO Defina un m. static para los vertices finales 
			Predicate<Ejercicio2Vertex> es_terminal = Ejercicio2Vertex.goal();
			
			var gp = TestsPI5.testGreedy(GraphsPI5.greedyEjercicio2Graph(v_inicial, es_terminal));
			TestsPI5.toConsole("Voraz", gp, SolucionEjercicio2::of);
			
			var path = TestsPI5.testAStar(GraphsPI5.Ejercicio2Graph(v_inicial, es_terminal), gp);
			TestsPI5.toConsole("A*", path, SolucionEjercicio2::of);
			
			path = TestsPI5.testPDR(GraphsPI5.Ejercicio2Graph(v_inicial, es_terminal), gp);
			TestsPI5.toConsole("PDR", path, SolucionEjercicio2::of);
			
			path = TestsPI5.testBT(GraphsPI5.Ejercicio2Graph(v_inicial, es_terminal), gp);
			TestsPI5.toConsole("BT", path, SolucionEjercicio2::of);
			
			TestsPI5.line("*");

		});
	}

}
