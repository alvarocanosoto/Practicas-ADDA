package ejercicio1;


import java.io.IOException;
import java.util.List;
import java.util.Set;

import _datos.DatosEjercicio1;
import _datos.DatosEjercicio1.Tipo;
import _datos.DatosEjercicio1.Variedad;
import us.lsi.common.String2;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve.AuxGrammar;

public class Ejercicio1PLE {
	public static void ejemplo1_model() throws IOException {
		//Leer Datos de entrada
		DatosEjercicio1.iniDatos("ficheros/Ejercicio1DatosEntrada1.txt");

//		System.out.println("variedades::::" + variedades );
//		tipos = DatosEjercicio1.getListaTipos();
//		variedades = DatosEjercicio1.getListaVariedades();
		
		//si cambia el fichero de datos de entrada, cambiar tambien el nº del .lp para no sobreescribirlo
		//Pasar de LSI a Gurobi
		AuxGrammar.generate						//generar un archivo gurobi
				(DatosEjercicio1.class,				//la clase que utilizo
				"lsi_models/ejercicio1.lsi",		//de donde saco el modelo
				"gurobi_models/Ejercicio1-1.lp"); //donde lo llevo
		//Usar Gurobi
		GurobiSolution solution = GurobiLp.gurobi("gurobi_models/Ejercicio1-1.lp");
		//Interpretar solucion
		String2.toConsole("\nSolucion PLE: %s", solution.toString((s,d)->d>0.).substring(2));
	
//		Locale.setDefault(new Locale("en", "US"));
//		System.out.println(solution.toString((s,d)->d>0.));
	}
	
	public static void ejemplo1_model2() throws IOException {
		//Leer Datos de entrada
		DatosEjercicio1.iniDatos("ficheros/Ejercicio1DatosEntrada2.txt");

//		System.out.println("variedades::::" + variedades );
//		tipos = DatosEjercicio1.getListaTipos();
//		variedades = DatosEjercicio1.getListaVariedades();
		
		//si cambia el fichero de datos de entrada, cambiar tambien el nº del .lp para no sobreescribirlo
		//Pasar de LSI a Gurobi
		AuxGrammar.generate						//generar un archivo gurobi
				(DatosEjercicio1.class,				//la clase que utilizo
				"lsi_models/ejercicio1.lsi",		//de donde saco el modelo
				"gurobi_models/Ejercicio1-2.lp"); //donde lo llevo
		//Usar Gurobi
		GurobiSolution solution = GurobiLp.gurobi("gurobi_models/Ejercicio1-2.lp");
		//Interpretar solucion
		String2.toConsole("\nSolucion PLE: %s", solution.toString((s,d)->d>0.).substring(2));
	
//		Locale.setDefault(new Locale("en", "US"));
//		System.out.println(solution.toString((s,d)->d>0.));
	}
	
	public static void ejemplo1_model3() throws IOException {
		//Leer Datos de entrada
		DatosEjercicio1.iniDatos("ficheros/Ejercicio1DatosEntrada3.txt");

//		System.out.println("variedades::::" + variedades );
//		tipos = DatosEjercicio1.getListaTipos();
//		variedades = DatosEjercicio1.getListaVariedades();
		
		//si cambia el fichero de datos de entrada, cambiar tambien el nº del .lp para no sobreescribirlo
		//Pasar de LSI a Gurobi
		AuxGrammar.generate						//generar un archivo gurobi
				(DatosEjercicio1.class,				//la clase que utilizo
				"lsi_models/ejercicio1.lsi",		//de donde saco el modelo
				"gurobi_models/Ejercicio1-3.lp"); //donde lo llevo
		//Usar Gurobi
		GurobiSolution solution = GurobiLp.gurobi("gurobi_models/Ejercicio1-3.lp");
		//Interpretar solucion
		String2.toConsole("\nSolucion PLE: %s", solution.toString((s,d)->d>0.).substring(2));
	
//		Locale.setDefault(new Locale("en", "US"));
//		System.out.println(solution.toString((s,d)->d>0.));
	}
	
	
	
	public static void main(String[] args) throws IOException {	
		ejemplo1_model();
//		ejemplo1_model2();
//		ejemplo1_model3();


	}

}
