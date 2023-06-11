package ejercicios.tests.manual;

import java.util.List;

import _datos.DatosEjercicio4;
import _utils.TestsPI5;
import ejercicios.ejercicio4.manual.Ejercicio4BT;
import us.lsi.common.String2;

public class TestsEM4 {

	public static void main(String[] args) {
		List.of(1,2).forEach(num_test -> {
			DatosEjercicio4.iniDatos("ficheros/Ejercicio4DatosEntrada"+num_test+".txt");
			Ejercicio4BT.search();
			Ejercicio4BT.getSoluciones().forEach(s -> String2.toConsole("Solucion obtenida: %s\n", s));
			TestsPI5.line("*");
		});
	}
	
}
