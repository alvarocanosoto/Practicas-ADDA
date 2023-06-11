package ejercicios;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.alg.color.GreedyColoring;
import org.jgrapht.alg.interfaces.VertexColoringAlgorithm.Coloring;
import org.jgrapht.graph.DefaultEdge;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Style;

public class Ejercicio3 {
	public static void todosLosApartados(Graph<String, DefaultEdge> g, String file) {

		GreedyColoring<String, DefaultEdge> alg = new GreedyColoring<>(g);
		Coloring<String> coloreado = alg.getColoring();
		System.out.println("Numero de franjas horarias necesarias: " + coloreado.getNumberColors());
		System.out.println("Actividades para impartirse en paralelo por franja horaria: ");

		List<Set<String>> comp = coloreado.getColorClasses();
		for(int i = 0; i < comp.size(); i++) {
			System.out.println("Franja numero " + (i+1) + ": " + comp.get(i));
		}


		Map<String, Integer> map = coloreado.getColors();
		String fileRes = "resultados/ejercicio3/" + file  + "B.gv";
		GraphColors.toDot(g, fileRes,
				v -> v, 
				e -> "", 
				v -> GraphColors.color(map.get(v)), 
				e -> GraphColors.style(Style.bold));

		System.out.println("Se ha generado " + fileRes);
	}
}