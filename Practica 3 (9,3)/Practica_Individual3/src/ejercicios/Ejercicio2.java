package ejercicios;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;

import org.jgrapht.alg.shortestpath.FloydWarshallShortestPaths;
import org.jgrapht.alg.tour.HeldKarpTSP;
import org.jgrapht.graph.SimpleWeightedGraph;

import datos.Ciudad2;

import datos.Trayecto;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.colors.GraphColors.Style;

import us.lsi.graphs.views.SubGraphView;

public class Ejercicio2 {

	//	Determine cuántos grupos de ciudades hay y cuál es su composición. Dos
	//	ciudades pertenecen al mismo grupo si están relacionadas directamente entre sí o
	//	si existen algunas ciudades intermedias que las relacionan. Muestre el grafo
	//	configurando su apariencia de forma que se coloree cada grupo de un color
	//	diferente.

	private static Color asignaColor(Ciudad2 v, List<Set<Ciudad2>> ls, ConnectivityInspector<Ciudad2, Trayecto> alg ) {
		Color[] vc = Color.values();
		Set<Ciudad2> s = alg.connectedSetOf(v);
		return vc [ls.indexOf(s)];
	}

	public static List<Set<Ciudad2>> apartadoA(Graph<Ciudad2, Trayecto> g, String file, String nombreVista) {
		ConnectivityInspector<Ciudad2, Trayecto> p = new ConnectivityInspector<>(g);
		List<Set<Ciudad2>> ls = p.connectedSets();

		String fileRes = "resultados/ejercicio2/" + file + nombreVista +  ".gv";
		GraphColors.toDot(g, fileRes,
				v->v.id(), e->"",
				v-> GraphColors.color(asignaColor(v, ls, p)),
				e-> GraphColors.color(asignaColor(g.getEdgeSource(e), ls, p)));

		System.out.println("Hay " + ls.size() + " componentes conexas");
		System.out.println("Se ha generado " + fileRes);
		return ls;
	}

	//	Determine cuál es el grupo de ciudades a visitar si se deben elegir las ciudades
	//	conectadas entre sí que maximice la suma total de las puntuaciones. Muestre el
	//	grafo configurando su apariencia de forma que se resalten dichas ciudades.

	public static void apartadoB(Graph<Ciudad2, Trayecto> g, String file, String nombreVista) {
		//obtengo componentes conexas
		List<Set<Ciudad2>> ls = apartadoA(g, file, nombreVista);
		//funcion de sumatorio de puntuaciones
		Function<Set<Ciudad2>, Integer> algoritmo = elem -> elem.stream().map(Ciudad2::puntuacion).reduce(0, (a, b) -> a + b);

		//calculo sumas para cada componente conexa
		Integer puntuacion = ls.stream().map(algoritmo).mapToInt(x->x).max().getAsInt();//calculo la puntuación maxima para comprobar		
		Set<Ciudad2> res = ls.stream().reduce((a,b)-> algoritmo.apply(a) > algoritmo.apply(b)? a:b).get();//me quedo con el conjunto de vertices con mayor puntuacion
		System.out.println("Los vertices que tienen la suma maxima de puntuaciones son: " + res + " y la suma de sus puntuaciones es: " + puntuacion);//imprimo para comprobar
		Graph<Ciudad2, Trayecto> g2 = SubGraphView.of(g, res);//creo una subvista con el conjunto de mayor puntuacion

		String fileRes = "resultados/ejercicio2/" + file + nombreVista +  ".gv";
		GraphColors.toDot(g, fileRes,
				v->v.id(), e->"",
				v-> GraphColors.colorIf(Color.blue, g2.vertexSet().contains(v)),
				e-> GraphColors.colorIf(Color.blue, g2.edgeSet().contains(e)));


		System.out.println("Se ha generado " + fileRes);	
	}


	//	Determine cuál es el grupo de ciudades a visitar si se deben elegir las ciudades
	//	conectadas entre sí que den lugar al camino cerrado de menor precio que pase por
	//	todas ellas. Muestre el grafo configurando su apariencia de forma que se resalte
	//	dicho camino.

	public static void apartadoC(SimpleWeightedGraph<Ciudad2, Trayecto> g, String file, String nombreVista) {
		//obtengo componentes conexas
		List<Set<Ciudad2>> ls = apartadoA(g, file, nombreVista);
		//algoritmo hamilton problema del viajante
		HeldKarpTSP<Ciudad2, Trayecto> alg = new HeldKarpTSP<>();
		List<GraphPath<Ciudad2, Trayecto>> listaCaminos = new ArrayList<>(); //lista donde almaceno los caminos
		//calculo caminos para cada componente conexa
		ls.forEach(y -> {
			Graph<Ciudad2, Trayecto> g2 = SubGraphView.of(g, y);
			GraphPath<Ciudad2, Trayecto> r = alg.getTour(g2);
			listaCaminos.add(r);
		});

		GraphPath<Ciudad2, Trayecto> res = //me quedo con el camino con menor longitud
				listaCaminos.stream()
				.reduce((a,b)-> a.getWeight() < b.getWeight()? a:b)
				.get();

		System.out.println("Los vertices del camino de menor precio son: " + res.getVertexList());
		System.out.println("El coste es: " + res.getWeight());

		Set<Ciudad2> verticesCamino = new HashSet<Ciudad2>(res.getVertexList());
		Set<Trayecto> aristasCamino = new HashSet<Trayecto>(res.getEdgeList());

		String fileRes = "resultados/ejercicio2/" + file + nombreVista +  ".gv";
		GraphColors.toDot(g, fileRes,
				v->v.id(), e->e.precio().toString(),
				v-> GraphColors.colorIf(Color.blue, verticesCamino.contains(v)),
				e-> GraphColors.colorIf(Color.blue, aristasCamino.contains(e)));

		System.out.println("Se ha generado " + fileRes);

	}		

	//	De cada grupo de ciudades, determinar cuáles son las 2 ciudades (no conectadas
	//	directamente entre sí) entre las que se puede viajar en un menor tiempo. Muestre
	//	el grafo configurando su apariencia de forma que se resalten las ciudades y el
	//	camino entre ellas.

	public static void apartadoD(SimpleWeightedGraph<Ciudad2, Trayecto> g, String file, String nombreVista) {

		ConnectivityInspector<Ciudad2, Trayecto> p = new ConnectivityInspector<>(g);
		List<Set<Ciudad2>> ls = p.connectedSets();
		List<GraphPath<Ciudad2, Trayecto>> listaCaminos = new ArrayList<>();//lista donde almaceno el camino más corto para cada componente
		ls.forEach(x -> { 		//itero en cada componente conexa
			Graph<Ciudad2, Trayecto> g2 = SubGraphView.of(g, x);

			List<Ciudad2> vertices = g2.vertexSet().stream().toList();	

			FloydWarshallShortestPaths<Ciudad2, Trayecto> a = new FloydWarshallShortestPaths<>(g2); //algoritmo camino minimo
			Set<GraphPath<Ciudad2, Trayecto>> s = new HashSet<>();

			for(Ciudad2 v : vertices) { //para cada vertice
				List<Ciudad2> vertices2 = vertices;
				vertices2 = vertices2.stream() //guardo en una lista el conjunto de vertices del grafo borrando los adyacentes del vertice donde estoy
						.filter(y -> !Graphs.successorListOf(g2, v).contains(y) && !Graphs.predecessorListOf(g2, v).contains(y))
						.toList();

				for(Ciudad2 v2 : vertices2) { 
					if(a.getPath(v,v2).getWeight()!=0) s.add(a.getPath(v,v2)); //calculo los caminos minimos entre mi vertice y cada uno de los que no son adyacentes a el
				}
			}

			GraphPath<Ciudad2, Trayecto> caminoMinimo = s.stream().reduce((a3, b) -> a3.getWeight() < b.getWeight()? a3: b).get(); //voy comparando peso y me quedo con el camino con menor peso
			listaCaminos.add(caminoMinimo); //lo añado a la lista de caminos (este ya sí es el camino de menor peso en esta componente conexa
			System.out.println("El camino que realiza es: " + caminoMinimo + " y su peso es: " + caminoMinimo.getWeight());

			Predicate<Ciudad2> pv1 = c-> caminoMinimo.getVertexList().contains(c);
			Predicate<Trayecto> pa1 = e -> caminoMinimo.getEdgeList().contains(e);  

			Graph<Ciudad2, Trayecto> gRes = SubGraphView.of(g, pv1, pa1);
			int componente = ls.indexOf(x) + 1;
			String fileRes = "resultados/ejercicio2/" + file + nombreVista + "-componente-" + componente +  ".gv";
			GraphColors.toDot(g, fileRes,
					v->v.id(), e->e.precio().toString(),
					v-> GraphColors.styleIf(Style.bold, gRes.vertexSet().contains(v)),
					e-> GraphColors.styleIf(Style.bold, gRes.edgeSet().contains(e)));

			System.out.println("Se ha generado " + fileRes);	

		});

	}	

}
