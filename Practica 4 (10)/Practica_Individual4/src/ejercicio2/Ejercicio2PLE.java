package ejercicio2;


import java.io.IOException;
import java.util.Locale;

import _datos.DatosEjercicio2;
import us.lsi.common.String2;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve.AuxGrammar;

public class Ejercicio2PLE {
	
	public static void ejercicio2_model() throws IOException {
		//Leer Datos de entrada
		DatosEjercicio2.iniDatos("ficheros/Ejercicio2DatosEntrada1.txt");
		
		//si cambia el fichero de datos de entrada, cambiar tambien el nº del .lp para no sobreescribirlo
		//Pasar de LSI a Gurobi
		AuxGrammar.generate						//generar un archivo gurobi
				(DatosEjercicio2.class,				//la clase que utilizo
				"lsi_models/ejercicio2.lsi",		//de donde saco el modelo
				"gurobi_models/Ejercicio2-1.lp"); //donde lo llevo
		//Usar Gurobi
		GurobiSolution solution = GurobiLp.gurobi("gurobi_models/Ejercicio2-1.lp");
		//Interpretar solucion
//		String2.toConsole("\nSolucion PLE: %s", solution.toString((s,d)->d>0.).substring(2));
	
		Locale.setDefault(new Locale("en", "US"));
		System.out.println(solution.toString((s,d)->d>0.));
	}
	
	public static void ejercicio2_model2() throws IOException {
		//Leer Datos de entrada
		DatosEjercicio2.iniDatos("ficheros/Ejercicio2DatosEntrada2.txt");
		
		//si cambia el fichero de datos de entrada, cambiar tambien el nº del .lp para no sobreescribirlo
		//Pasar de LSI a Gurobi
		AuxGrammar.generate						//generar un archivo gurobi
				(DatosEjercicio2.class,				//la clase que utilizo
				"lsi_models/ejercicio2.lsi",		//de donde saco el modelo
				"gurobi_models/Ejercicio2-2.lp"); //donde lo llevo
		//Usar Gurobi
		GurobiSolution solution = GurobiLp.gurobi("gurobi_models/Ejercicio2-2.lp");
		//Interpretar solucion
//		String2.toConsole("\nSolucion PLE: %s", solution.toString((s,d)->d>0.).substring(2));
	
		Locale.setDefault(new Locale("en", "US"));
		System.out.println(solution.toString((s,d)->d>0.));
	}
	
	public static void ejercicio2_model3() throws IOException {
		//Leer Datos de entrada
		DatosEjercicio2.iniDatos("ficheros/Ejercicio2DatosEntrada3.txt");
		
		//si cambia el fichero de datos de entrada, cambiar tambien el nº del .lp para no sobreescribirlo
		//Pasar de LSI a Gurobi
		AuxGrammar.generate						//generar un archivo gurobi
				(DatosEjercicio2.class,				//la clase que utilizo
				"lsi_models/ejercicio2.lsi",		//de donde saco el modelo
				"gurobi_models/Ejercicio2-3.lp"); //donde lo llevo
		//Usar Gurobi
		GurobiSolution solution = GurobiLp.gurobi("gurobi_models/Ejercicio2-3.lp");
		//Interpretar solucion
//		String2.toConsole("\nSolucion PLE: %s", solution.toString((s,d)->d>0.).substring(2));
	
		Locale.setDefault(new Locale("en", "US"));
		System.out.println(solution.toString((s,d)->d>0.));
	}
	
	public static void main(String[] args) throws IOException {	
		ejercicio2_model();
//		ejercicio2_model2();
//		ejercicio2_model3();


	}
	

}
