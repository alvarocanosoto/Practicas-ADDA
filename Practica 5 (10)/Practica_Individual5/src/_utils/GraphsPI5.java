package _utils;

import java.util.function.Predicate;

import ejercicios.ejercicio1.Ejercicio1Edge;
import ejercicios.ejercicio1.Ejercicio1Heuristic;
import ejercicios.ejercicio1.Ejercicio1Vertex;
import ejercicios.ejercicio2.Ejercicio2Edge;
import ejercicios.ejercicio2.Ejercicio2Heuristic;
import ejercicios.ejercicio2.Ejercicio2Vertex;
import ejercicios.ejercicio3.Ejercicio3Edge;
import ejercicios.ejercicio3.Ejercicio3Heuristic;
import ejercicios.ejercicio3.Ejercicio3Vertex;
import ejercicios.ejercicio4.Ejercicio4Edge;
import ejercicios.ejercicio4.Ejercicio4Heuristic;
import ejercicios.ejercicio4.Ejercicio4Vertex;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

// Clase Factoria para crear los grafos de los ejemplos y ejercicios
public class GraphsPI5 {
	
	// Ejercicio1: Grafo NO Greedy
	public static EGraph<Ejercicio1Vertex, Ejercicio1Edge>
	Ejercicio1Graph(Ejercicio1Vertex v_inicial, Predicate<Ejercicio1Vertex> es_terminal) { 
		return EGraph.virtual(v_inicial, es_terminal, PathType.Sum, Type.Max)
				.goalHasSolution(Ejercicio1Vertex.goalHasSolution())
				.heuristic(Ejercicio1Heuristic::heuristic).build();
	}
	
	// Ejercicio1: Grafo Greedy
	public static EGraph<Ejercicio1Vertex, Ejercicio1Edge> 
	greedyEjercicio1Graph(Ejercicio1Vertex v_inicial, Predicate<Ejercicio1Vertex> es_terminal) { 
		return EGraph.virtual(v_inicial,es_terminal,PathType.Sum, Type.Max)
				.greedyEdge(Ejercicio1Vertex::greedyEdge)
				.goalHasSolution(Ejercicio1Vertex.goalHasSolution())
				.heuristic(Ejercicio1Heuristic::heuristic).build();
	}

	// Ejercicio2: Grafo NO Greedy
	public static EGraph<Ejercicio2Vertex, Ejercicio2Edge>
	Ejercicio2Graph(Ejercicio2Vertex v_inicial, Predicate<Ejercicio2Vertex> es_terminal) { 
		return EGraph.virtual(v_inicial, es_terminal, PathType.Sum, Type.Min)
				.goalHasSolution(Ejercicio2Vertex.goalHasSolution())
				.heuristic(Ejercicio2Heuristic::heuristic).build();
	}
	
	// Ejercicio2: Grafo Greedy
	public static EGraph<Ejercicio2Vertex, Ejercicio2Edge> 
	greedyEjercicio2Graph(Ejercicio2Vertex v_inicial, Predicate<Ejercicio2Vertex> es_terminal) { 
		return EGraph.virtual(v_inicial,es_terminal,PathType.Sum, Type.Min)
				.greedyEdge(Ejercicio2Vertex::greedyEdge)
				.goalHasSolution(Ejercicio2Vertex.goalHasSolution())
				.heuristic(Ejercicio2Heuristic::heuristic).build();
	}

	// Ejercicio3: Grafo NO Greedy
	public static EGraph<Ejercicio3Vertex, Ejercicio3Edge>
	Ejercicio3Graph(Ejercicio3Vertex v_inicial, Predicate<Ejercicio3Vertex> es_terminal) {
		return EGraph.virtual(v_inicial, es_terminal, PathType.Sum, Type.Max)
				.goalHasSolution(Ejercicio3Vertex.goalHasSolution())
				.heuristic(Ejercicio3Heuristic::heuristic).build();
	}
	
	// Ejercicio3: Grafo Greedy
	public static EGraph<Ejercicio3Vertex, Ejercicio3Edge> 
	greedyEjercicio3Graph(Ejercicio3Vertex v_inicial, Predicate<Ejercicio3Vertex> es_terminal) {
		return EGraph.virtual(v_inicial,es_terminal,PathType.Sum, Type.Max)
				.greedyEdge(Ejercicio3Vertex::greedyEdge)
				.goalHasSolution(Ejercicio3Vertex.goalHasSolution())
				.heuristic(Ejercicio3Heuristic::heuristic).build();
	}
	
	// Ejercicio4: Grafo NO Greedy
	public static EGraph<Ejercicio4Vertex, Ejercicio4Edge>
	Ejercicio4Graph(Ejercicio4Vertex v_inicial, Predicate<Ejercicio4Vertex> es_terminal) {
		return EGraph.virtual(v_inicial, es_terminal, PathType.Sum, Type.Max)
				.goalHasSolution(Ejercicio4Vertex.goalHasSolution())
				.heuristic(Ejercicio4Heuristic::heuristic).build();
	}

	// Ejercicio4: Grafo Greedy
	public static EGraph<Ejercicio4Vertex, Ejercicio4Edge> 
	greedyEjercicio4Graph(Ejercicio4Vertex v_inicial, Predicate<Ejercicio4Vertex> es_terminal) {
		return EGraph.virtual(v_inicial,es_terminal,PathType.Sum, Type.Max)
				.greedyEdge(Ejercicio4Vertex::greedyEdge)
				.goalHasSolution(Ejercicio4Vertex.goalHasSolution())
				.heuristic(Ejercicio4Heuristic::heuristic).build();
	}
}
