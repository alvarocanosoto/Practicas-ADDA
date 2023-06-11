package ejercicios.ejercicio4;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jgrapht.Graph;

import _datos.DatosEjercicio4;
import _datos.DatosEjercicio4.Cliente;
import _datos.DatosEjercicio4.Conexion;
import us.lsi.common.List2;
import us.lsi.common.Set2;
import us.lsi.graphs.virtual.VirtualVertex;

// Uso el segundo modelo
public record Ejercicio4Vertex(Integer index, Set<Integer> remainingClientes ,List<Integer> clientesVisitados, Integer km_Recorridos) 
implements VirtualVertex<Ejercicio4Vertex,Ejercicio4Edge,Integer> {

	public static Ejercicio4Vertex of(Integer i, Set<Integer> rest ,List<Integer> clien, Integer kms) {
		return new Ejercicio4Vertex(i, rest, clien, kms);
	}

	@Override
	public List<Integer> actions() { //tengo tantas alternativas como vértices adyacentes, y el valor será el índice del vértice destino
		// TODO Alternativas de un vertice
		List<Integer> res = List2.empty();
		if(goal().test(this)) return res; //si he terminado no devuelvo nada
		else if(this.clientesVisitados().size()==DatosEjercicio4.getNumVertices()+1) { //si estoy en el último vértice, vuelvo al inicio
			if(vuelve()) return List2.empty();
			else return res;
		}
		else {
			res = this.remainingClientes.stream()
					.filter(x -> adyacentes(x, this.index) ).toList();			
			return res;
		}

	}

	@Override
	public Ejercicio4Vertex neighbor(Integer a) {
		// TODO Vertice siguiente al actual segun la alternativa a 
		//actualizar remainingClientes
		Set<Integer> remainingClientes2 = Set2.copy(this.remainingClientes());
		remainingClientes2.remove(a);
		//actualizar clientesVisitados
		List<Integer> clientesVisitados2 = List2.copy(this.clientesVisitados);
		clientesVisitados2.add(a);
		//actualizar km_Recorridos
		Integer kilometros2 = this.km_Recorridos + DatosEjercicio4.getDistancia(this.index, a).intValue();
		
		return new Ejercicio4Vertex(a, remainingClientes2, clientesVisitados2, kilometros2);
	}

	@Override
	public Ejercicio4Edge edge(Integer a) {
		return Ejercicio4Edge.of(this,this.neighbor(a),a);
	}

	// Se explica en practicas.
	public Ejercicio4Edge greedyEdge() {
		Integer clienteActual = this.index;
		Comparator<Integer> cmp = Comparator.comparing(x -> DatosEjercicio4.getBeneficio(x)-obtienePesoArista(clienteActual, x));
		List<Integer> interseccion = this.remainingClientes.stream().filter(x -> adyacentes(x, this.index)).toList();
		Integer max = interseccion.stream().max(cmp).orElse(0);
		return edge(max);
	}

	public Double obtienePesoArista(Integer a, Integer b) {
		Cliente clienteA = DatosEjercicio4.getCliente(a);
		Cliente clienteB = DatosEjercicio4.getCliente(b);
		return DatosEjercicio4.g.getEdge(clienteA, clienteB).distancia();
	}
	

	public Set<Integer> obtieneVecinos(Integer a){
		Cliente clienteA = DatosEjercicio4.getCliente(a);
		List<Cliente> adyacentes1 = DatosEjercicio4.g.incomingEdgesOf(clienteA).stream().map(x -> DatosEjercicio4.g.getEdgeSource(x)).toList();
		List<Cliente> adyacentes2 = DatosEjercicio4.g.outgoingEdgesOf(clienteA).stream().map(x -> DatosEjercicio4.g.getEdgeTarget(x)).toList();
		List<Cliente> adyacentes3 = List2.concat(adyacentes1, adyacentes2);
		Set<Integer> adyacentes = adyacentes3.stream().map(x -> x.id()).collect(Collectors.toSet());
		adyacentes.stream().filter(x -> !this.clientesVisitados.contains(x) && x !=a);
		adyacentes.remove(a);
		adyacentes.removeAll(this.clientesVisitados);
		return adyacentes;
	}

	public static Ejercicio4Vertex initial() {
		// TODO Auto-generated method stub
		Set<Integer> setVertices = DatosEjercicio4.g.vertexSet().stream().map(Cliente::id).collect(Collectors.toSet());
		return new Ejercicio4Vertex(0,setVertices, List2.of(0), 0);
	}

	public static Predicate<Ejercicio4Vertex> goal() {
		// TODO Auto-generated method stub
		Predicate<Ejercicio4Vertex> pred =  p -> p.clientesVisitados.size()==DatosEjercicio4.getNumVertices()+1; //paro cuando he recorrido todos los vértices

		return pred;
	}

	public static Predicate<Ejercicio4Vertex> goalHasSolution() { //tiene solucion si he recorrido todos los vértices
		// TODO Auto-generated method stub
		Predicate<Ejercicio4Vertex> pred =  p -> p.remainingClientes.isEmpty() && p.clientesVisitados.size()==DatosEjercicio4.getNumVertices()+1;
		return pred;
	}
	
	public Boolean adyacentes(Integer x, Integer y) {
		return DatosEjercicio4.existeArista(y, x) || DatosEjercicio4.existeArista(x, y);
	}
	
	public Boolean vuelve() {
		Integer ultimo = this.clientesVisitados.get(DatosEjercicio4.getNumVertices()-1);
		return adyacentes(ultimo, 0);
	}

}
