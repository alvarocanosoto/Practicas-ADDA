package ejercicios.tests.manual;

import java.util.List;

import _datos.DatosEjercicio3;
import _utils.TestsPI5;
import ejercicios.ejercicio3.manual.Ejercicio3BT;
import us.lsi.common.String2;

public class TestsEM3 {

	public static void main(String[] args) {
		List.of(1).forEach(num_test -> {
			DatosEjercicio3.iniDatos("ficheros/Ejercicio3DatosEntrada"+num_test+".txt");
			Ejercicio3BT.search();
			Ejercicio3BT.getSoluciones().forEach(s -> String2.toConsole("Solucion obtenida: %s\n", s));
			TestsPI5.line("*");
		});
	}
	
}
