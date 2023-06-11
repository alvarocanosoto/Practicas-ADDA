package ejercicios.ejercicio4.manual;


import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;

import _datos.DatosEjercicio4;
import _datos.DatosEjercicio4.Cliente;
import _datos.DatosEjercicio4.Conexion;
import us.lsi.common.List2;
import us.lsi.common.Set2;

public record Ejercicio4Problem(Integer index, Set<Integer> remainingClientes ,List<Integer> clientesVisitados, Double km_Recorridos) 
{

	public static Ejercicio4Problem of(Integer i, Set<Integer> rest ,List<Integer> clien, Double kms) {
		return new Ejercicio4Problem(i, rest, clien, kms);
	}

	// TODO Consulte las clases GraphsPI5 y TestPI5 
	public static Predicate<Ejercicio4Problem> goal(){
		return p -> p.clientesVisitados.size()==DatosEjercicio4.getNumVertices()+1;
	}

	public static Predicate<Ejercicio4Problem> goalHasSolution(){
		return p -> p.remainingClientes.isEmpty() && p.clientesVisitados.get(p.clientesVisitados.size()-1)==0;
	}

	public static Ejercicio4Problem initial() {
		Set<Integer> setVertices = DatosEjercicio4.g.vertexSet().stream().map(Cliente::id).collect(Collectors.toSet());
		return of(0,setVertices, List2.of(0), 0.);
	}


	@Override
	public String toString() {
		return "Ejercicio4Problem [index=" + index + ", remainingClientes=" + remainingClientes + ", clientesVisitados="
				+ clientesVisitados + ", km_Recorridos=" + km_Recorridos + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(clientesVisitados, index, km_Recorridos, remainingClientes);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ejercicio4Problem other = (Ejercicio4Problem) obj;
		return Objects.equals(clientesVisitados, other.clientesVisitados) && Objects.equals(index, other.index)
				&& Objects.equals(km_Recorridos, other.km_Recorridos)
				&& Objects.equals(remainingClientes, other.remainingClientes);
	}

	public List<Integer> actions() {
		// TODO Alternativas de un vertice .
		List<Integer> res = List2.empty();
		if(goal().test(this)) return res; //si he terminado no devuelvo nada
		else if(this.clientesVisitados().size()==DatosEjercicio4.getNumVertices()+1) { //si estoy en el último vértice, vuelvo al inicio
			if(vuelve()) return List2.empty();
			else return res;
		}
		else {
			Cliente clienteActual = DatosEjercicio4.getCliente(this.clientesVisitados().get(this.clientesVisitados().size()-1));
			List<Integer> clientesAdyacentes = Graphs.neighborListOf(DatosEjercicio4.g, clienteActual).stream()
					.map(x ->x.id())
					.toList();
			res.addAll(List2.intersection(this.remainingClientes, clientesAdyacentes));

//			res = this.remainingClientes.stream()
//					.filter(x -> DatosEjercicio4.existeArista(this.index, x))
//					.toList();
			return res;
		}

	}


	public Ejercicio4Problem neighbor(Integer a) {
		// TODO Vertice siguiente al actual segun la alternativa a

		Set<Integer> remainingClientes2 = Set2.copy(this.remainingClientes());
		remainingClientes2.remove(a);
		//actualizar clientesVisitados
		List<Integer> clientesVisitados2 = List2.copy(this.clientesVisitados);
		clientesVisitados2.add(a);
		//actualizar km_Recorridos
		Double kilometros2 = this.km_Recorridos + DatosEjercicio4.getDistancia(this.index, a);

		return Ejercicio4Problem.of(a, remainingClientes2, clientesVisitados2, kilometros2);
	}

	public Double heuristic() {
		Double res = 0.;
		List<Integer> clientesPendientes = IntStream.range(0, DatosEjercicio4.getNumVertices()).boxed().toList();
		List<Integer> clientesPendientes2 = List2.intersection(clientesPendientes, clientesVisitados());
		List<Cliente> clientes = clientesPendientes2.stream().map(x -> DatosEjercicio4.getCliente(x)).sorted(Comparator.comparing(Cliente::beneficio).reversed()).toList();
		for(Cliente c:clientes) {
			res+=c.beneficio()-clientes.indexOf(c);
		}
		return res;
	}




	public Boolean adyacentes(Integer x, Integer y) {
		return DatosEjercicio4.existeArista(y, x) || DatosEjercicio4.existeArista(x, y);
	}


	public Boolean existeAristaExtricto(Integer x, Integer y) {
		Graph<Cliente, Conexion> grafo = DatosEjercicio4.g;
		Cliente cliente1 = DatosEjercicio4.getCliente(x);
		Cliente cliente2 = DatosEjercicio4.getCliente(y);
		Boolean res = grafo.outgoingEdgesOf(cliente1).stream().anyMatch(a -> grafo.getEdgeTarget(a) == cliente2);
		System.out.println("Existe la arista entre " + x + " y " + y + "?: " + res);
		return res;
	}
	
	public Boolean vuelve() {
		Integer ultimo = this.clientesVisitados.get(DatosEjercicio4.getNumVertices()-1);
		return adyacentes(ultimo, 0);
	}
}
