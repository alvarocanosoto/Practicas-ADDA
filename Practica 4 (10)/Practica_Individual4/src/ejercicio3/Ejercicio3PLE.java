package ejercicio3;

import java.io.IOException;
import java.util.List;

import _datos.DatosEjercicio2;
import _datos.DatosEjercicio3;
import _datos.DatosEjercicio3.Investigador;
import _datos.DatosEjercicio3.Trabajo;
import us.lsi.common.String2;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve.AuxGrammar;

public class Ejercicio3PLE {

	
	public static void ejercicio3_model() throws IOException {
		//Leer Datos de entrada
		DatosEjercicio3.iniDatos("ficheros/Ejercicio3DatosEntrada1.txt");
		
		//si cambia el fichero de datos de entrada, cambiar tambien el nº del .lp para no sobreescribirlo
		//Pasar de LSI a Gurobi
		AuxGrammar.generate						//generar un archivo gurobi
				(DatosEjercicio3.class,				//la clase que utilizo
				"lsi_models/ejercicio3.lsi",		//de donde saco el modelo
				"gurobi_models/Ejercicio3-1.lp"); //donde lo llevo
		//Usar Gurobi
		GurobiSolution solution = GurobiLp.gurobi("gurobi_models/Ejercicio3-1.lp");
		//Interpretar solucion
		String2.toConsole("\nSolucion PLE: %s", solution.toString((s,d)->d>0.).substring(2));
	
//		Locale.setDefault(new Locale("en", "US"));
//		System.out.println(solution.toString((s,d)->d>0.));
	}
	
	public static void ejercicio3_model2() throws IOException {
		//Leer Datos de entrada
		DatosEjercicio3.iniDatos("ficheros/Ejercicio3DatosEntrada2.txt");
		
		//si cambia el fichero de datos de entrada, cambiar tambien el nº del .lp para no sobreescribirlo
		//Pasar de LSI a Gurobi
		AuxGrammar.generate						//generar un archivo gurobi
				(DatosEjercicio3.class,				//la clase que utilizo
				"lsi_models/ejercicio3.lsi",		//de donde saco el modelo
				"gurobi_models/Ejercicio3-2.lp"); //donde lo llevo
		//Usar Gurobi
		GurobiSolution solution = GurobiLp.gurobi("gurobi_models/Ejercicio3-2.lp");
		//Interpretar solucion
		String2.toConsole("\nSolucion PLE: %s", solution.toString((s,d)->d>0.).substring(2));
	
//		Locale.setDefault(new Locale("en", "US"));
//		System.out.println(solution.toString((s,d)->d>0.));
	}
	
	public static void ejercicio3_model3() throws IOException {
		//Leer Datos de entrada
		DatosEjercicio3.iniDatos("ficheros/Ejercicio3DatosEntrada3.txt");
		
		//si cambia el fichero de datos de entrada, cambiar tambien el nº del .lp para no sobreescribirlo
		//Pasar de LSI a Gurobi
		AuxGrammar.generate						//generar un archivo gurobi
				(DatosEjercicio3.class,				//la clase que utilizo
				"lsi_models/ejercicio3.lsi",		//de donde saco el modelo
				"gurobi_models/Ejercicio3-3.lp"); //donde lo llevo
		//Usar Gurobi
		GurobiSolution solution = GurobiLp.gurobi("gurobi_models/Ejercicio3-3.lp");
		//Interpretar solucion
		String2.toConsole("\nSolucion PLE: %s", solution.toString((s,d)->d>0.).substring(2));
	
//		Locale.setDefault(new Locale("en", "US"));
//		System.out.println(solution.toString((s,d)->d>0.));
	}
	
	public static void main(String[] args) throws IOException {	
		ejercicio3_model();
//		ejercicio3_model2();
//		ejercicio3_model3();


	}
	

}



