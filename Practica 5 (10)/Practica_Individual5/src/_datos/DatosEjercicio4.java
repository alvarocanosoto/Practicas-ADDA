package _datos;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

public class DatosEjercicio4 {
	
	private static int id_aux = 0;
	public record Conexion(int id, Double distancia) {
		public static Conexion ofFormat(String[] formato) {
			Integer id = id_aux++;
			Double dist = Double.valueOf(formato[2].trim());
			return new Conexion(id, dist);
		}
	}
	
	public record Cliente(int id, Double beneficio) {
		public static Cliente ofFormat(String[] formato) {
			Integer id = Integer.valueOf(formato[0].trim());
			Double benef = Double.valueOf(formato[1].trim());
			return new Cliente(id, benef);
		}
	}
	
	public static Graph<Cliente, Conexion> g;
	public static void iniDatos(String fichero) {
		g = GraphsReader.newGraph(fichero, Cliente::ofFormat, Conexion::ofFormat,
				Graphs2::simpleWeightedGraph);
		toConsole();
	}
	public static Integer getNumVertices() {
		return g.vertexSet().size();
	}

	
	public static Cliente getCliente(Integer i) { //no puedo acceder a los elementos de un set con i porque no están ordenados, me hace falta un id
		List<Cliente> vertices = new ArrayList<>(g.vertexSet());
		return vertices.stream().filter(x -> x.id()==i).findFirst().get();
	}
	public static Double getBeneficio(Integer i) {
		return getCliente(i).beneficio();
	}
	public static Boolean existeArista(Integer i, Integer j) {
		Cliente c1 = getCliente(i);
		Cliente c2 = getCliente(j);
		return g.containsEdge(c1, c2);
	}
	public static Double getDistancia(Integer i, Integer j) {
		Cliente c1 = getCliente(i);
		Cliente c2 = getCliente(j);
		return g.getEdge(c1, c2).distancia();
	}
	private static void toConsole() {
		System.out.println(g.vertexSet());
		System.out.println(g.edgeSet());
	}
	public static void main(String[] args) {
		iniDatos("ficheros/Ejercicio4DatosEntrada1.txt");
		System.out.println(getDistancia(2, 4));
	}
}
