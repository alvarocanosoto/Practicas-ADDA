package tests;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import ejercicios.Ejercicio3;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.common.Files2;
import us.lsi.graphs.Graphs2;

public class TestEjercicio3 {

	public static void main(String[] args) {
			testEjercicio3("PI3E3A_DatosEntrada");
			testEjercicio3("PI3E3B_DatosEntrada");
	}

	public static void testEjercicio3(String file) {
	
	Graph<String, DefaultEdge> g =
			Graphs2.simpleGraph(String::new, DefaultEdge::new, false);

	
	Files2.streamFromFile("ficheros/" + file + ".txt").forEach(linea -> {
		String[] v1 = linea.trim().split(":");
		String[] v2 = v1[1].trim().split(", ");

		for (int i = 0; i < v2.length - 1; i++) {
			if (!g.containsVertex(v2[i])) g.addVertex(v2[i]);
			for (int j = i + 1; j < v2.length; j++) {
				if (!g.containsVertex(v2[j])) g.addVertex(v2[j]);
				g.addEdge(v2[i], v2[j]);
			}
		}
		
	});
	
	//Generar el archivo gv
	String fileRes = "resultados/ejercicio3/" + file + ".gv";
	GraphColors.toDot(g, fileRes,
			v -> v, e-> "",
			v-> GraphColors.color(Color.black),
			e-> GraphColors.color(Color.black)
		);
	
	Ejercicio3.todosLosApartados(g, file);

}
	
}