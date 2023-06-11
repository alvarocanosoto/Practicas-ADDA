package tests;


import java.util.function.Predicate;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;

import datos.Personas;
import datos.Relacion;
import ejercicios.Ejercicio1;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

public class TestEjercicio1 {

	public static void main(String[] args) {
		testEjercicio1("PI3E1A_DatosEntrada");
		testEjercicio1("PI3E1B_DatosEntrada");

	}

	public static void testEjercicio1(String file) {

		//leer los datos de entrada
		Graph<Personas, Relacion> g =
				GraphsReader.newGraph("ficheros/"+file+".txt",
						Personas::ofFormat, Relacion::ofFormat, Graphs2::simpleDirectedGraph);

		//Generar el archivo gv
		String fileRes = "resultados/ejercicio1/" + file + ".gv";
		GraphColors.toDot(g, fileRes,
				v -> v.nombre(), e-> "",
				v-> GraphColors.color(Color.black),
				e-> GraphColors.color(Color.black)
				);

		//variables dependiendo del fichero que se use
		String nombrePersona0 = "Maria";
		String nombrePersona2 = "Carmen";
		String nombrePersona1 = "Rafael";
		String nombrePersona3 = "Maria";
		String nombrePersona4 = "Patricia";
		String nombrePersona5 = "Sara";

		if(file == "PI3E1B_DatosEntrada") {
			nombrePersona0 = "Raquel"; 
			nombrePersona2 = "Laura";
			nombrePersona1 = "Raquel";
			nombrePersona3 = "Angela";
			nombrePersona4 = "Julia";
			nombrePersona5 = "Alvaro";
		}


		//Apartado a)
		Predicate<Personas> pv1 = c-> g.edgesOf(c).stream().filter(x -> x.hijo() == c.id()).count()==2 //comprueba que están sus dos padres
				&& Graphs.predecessorListOf(g, c).stream().map(Personas::lugarNacimiento).distinct().limit(2).count() <= 1 //compruebo que han nacido en el mismo sitio
				&& Graphs.predecessorListOf(g, c).stream().map(Personas::anyo).distinct().limit(2).count() <= 1; //compruebo que han nacido el mismo año
		Predicate<Relacion> pa1 = Relacion -> true;   

		System.out.println("APARTADO A:");
		Ejercicio1.apartadoA(file, g, pv1, pa1, "Apartado A");
		System.out.println(" ");
		//Apartado b)
		System.out.println("APARTADO B:");
		Ejercicio1.apartadoB(file, g, nombrePersona0, "Apartado B");
		System.out.println(" ");

		//Apartado c)
		System.out.println("APARTADO C:");

		System.out.println(nombrePersona1 + " y " + nombrePersona2 + " son " + Ejercicio1.apartadoC(nombrePersona1, nombrePersona2, g));
		System.out.println(nombrePersona1 + " y " + nombrePersona5 + " son " + Ejercicio1.apartadoC(nombrePersona1, nombrePersona5, g));
		System.out.println(nombrePersona3 + " y " + nombrePersona4 + " son " + Ejercicio1.apartadoC(nombrePersona3, nombrePersona4, g));

		System.out.println(" ");

		//Apartado d)
		System.out.println("APARTADO D:");
		Ejercicio1.apartadoD(file, g, "Apartado D");
		System.out.println(" ");

		//Apartado e)
		System.out.println("APARTADO E:");
		Ejercicio1.apartadoE(g,file, "Apartado E");
		System.out.println(" ");


	}

	//obtener persona por id
	static public Personas getPersonaById(Integer id, Graph<Personas, Relacion> g ) {
		return g.vertexSet().stream().filter(v -> v.id().equals(id)).findFirst().orElse(null);
	}
}
