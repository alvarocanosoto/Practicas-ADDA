package ejercicios.ejercicio3;


import _datos.DatosEjercicio3;
import us.lsi.graphs.virtual.SimpleEdgeAction;

public record Ejercicio3Edge(Ejercicio3Vertex source, Ejercicio3Vertex target, Integer action, Double weight) 
               implements SimpleEdgeAction<Ejercicio3Vertex,Integer> {

	public static Ejercicio3Edge of(Ejercicio3Vertex v1, Ejercicio3Vertex v2, Integer a) {	
		// TODO La arista debe tener peso 
		Integer m = DatosEjercicio3.getTrabajos();
		Integer indiceTrabajoActual = v1.index()%m;
		Integer indiceInvActual = v1.index()/m;
		return new Ejercicio3Edge(v1, v2, a, a * DatosEjercicio3.getCalidad(indiceTrabajoActual) * 1.0);
	}
	@Override
	public String toString() {
		return String.format("%d; %.1f", action, weight);
	}
}

