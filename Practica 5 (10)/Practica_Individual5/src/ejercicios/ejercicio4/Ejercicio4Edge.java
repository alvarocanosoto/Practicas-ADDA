package ejercicios.ejercicio4;


import _datos.DatosEjercicio4;
import _datos.DatosEjercicio4.Cliente;
import us.lsi.graphs.virtual.SimpleEdgeAction;

public record Ejercicio4Edge(Ejercicio4Vertex source, Ejercicio4Vertex target, Integer action, Double weight) 
               implements SimpleEdgeAction<Ejercicio4Vertex,Integer> {

	public static Ejercicio4Edge of(Ejercicio4Vertex v1, Ejercicio4Vertex v2, Integer a) {	
		// TODO La arista debe tener peso 
		Cliente clienteActual = DatosEjercicio4.getCliente(v1.index());
		return new Ejercicio4Edge(v1, v2, a, clienteActual.beneficio() - v1.km_Recorridos()*0.01 );
	}
	
	@Override
	public String toString() {
		return String.format("%d; %.1f", action, weight);
	}

}


