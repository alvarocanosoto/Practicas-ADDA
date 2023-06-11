package ejercicios.ejercicio4;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import _datos.DatosEjercicio4;
import _datos.DatosEjercicio4.Cliente;
import us.lsi.common.List2;


public class Ejercicio4Heuristic {
	
	// Se explica en practicas.
	public static Double heuristic(Ejercicio4Vertex v1, Predicate<Ejercicio4Vertex> goal, Ejercicio4Vertex v2) {
		Double res = 0.;
		
		List<Integer> clientesPendientes = IntStream.range(0, DatosEjercicio4.getNumVertices()).boxed().toList();
		List<Integer> clientesPendientes2 = List2.intersection(clientesPendientes, v1.clientesVisitados());
		List<Cliente> clientes = clientesPendientes2.stream().map(x -> DatosEjercicio4.getCliente(x)).sorted(Comparator.comparing(Cliente::beneficio).reversed()).toList();
		for(Cliente c:clientes) {
			res+=c.beneficio()-clientes.indexOf(c);
		}
		return res;
		
		
			
	}
	
	
	//el orden es lo importante
	//miro los que tengo pendientes
	//a cada pendiente supongo que son adyacentes y cuando llegue me va a dar el beneficio posible (la penalizacion por el orden es pequeña, "1,2,...").
	//El orden que utilizo es el de la lista del segundo paso

		
}
