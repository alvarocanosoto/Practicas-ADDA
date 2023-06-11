package tests;

import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleWeightedGraph;

import datos.Ciudad2;
import datos.Trayecto;
import ejercicios.Ejercicio2;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

public class TestEjercicio2 {

	public static void main(String[] args) {
		testEjercicio2("PI3E2_DatosEntrada");

	}
	
	private static void testEjercicio2(String file) {
		Graph<Ciudad2, Trayecto> g = GraphsReader.newGraph("ficheros/" + file + ".txt", Ciudad2::ofFormat, Trayecto::ofFormat,
				Graphs2::simpleWeightedGraph);
		
		SimpleWeightedGraph<Ciudad2, Trayecto> g2 = GraphsReader.newGraph("ficheros/" + file + ".txt", 
				Ciudad2::ofFormat,
				Trayecto::ofFormat,
				Graphs2::simpleWeightedGraph,
				Trayecto::duracion
				);
		
		SimpleWeightedGraph<Ciudad2, Trayecto> g3 = GraphsReader.newGraph("ficheros/" + file + ".txt", 
				Ciudad2::ofFormat,
				Trayecto::ofFormat,
				Graphs2::simpleWeightedGraph,
				Trayecto::precio
				);
				
	
		String fileRes = "resultados/ejercicio2/"+ file + ".gv";
		GraphColors.toDot(g, fileRes, 
				v -> v.id(), e -> "",
				v -> GraphColors.color(Color.black), e -> GraphColors.color(Color.black));
		
		
		//Apartado A
		System.out.println("APARTADO A:");
		System.out.println(Ejercicio2.apartadoA(g, file, "Apartado A"));
		System.out.println(" ");

		//Apartado B
		System.out.println("APARTADO B:");
		Ejercicio2.apartadoB(g, file, "Apartado B");
		System.out.println(" ");
		
		//Apartado C
		System.out.println("APARTADO C:");
		Ejercicio2.apartadoC(g3, file, "Apartado C");
		System.out.println(" ");

		//Apartado D
		System.out.println("APARTADO D:");
		Ejercicio2.apartadoD(g2, file, "Apartado D");
		System.out.println(" ");

	}

}
