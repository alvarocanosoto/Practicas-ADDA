package _soluciones;

import java.util.List;

import org.jgrapht.GraphPath;

import _datos.DatosEjercicio1;
import _datos.DatosEjercicio1.Variedad;
import ejercicios.ejercicio1.Ejercicio1Edge;
import ejercicios.ejercicio1.Ejercicio1Vertex;
import us.lsi.common.List2;

public class SolucionEjercicio1 {
	
	public static SolucionEjercicio1 of_Range(List<Integer> ls) {
		return new SolucionEjercicio1(ls);
	}
	
	// Ahora en la PI5
	public static SolucionEjercicio1 of(GraphPath<Ejercicio1Vertex, Ejercicio1Edge> path) {
		List<Integer> ls = path.getEdgeList().stream().map(e -> e.action()).toList();
		SolucionEjercicio1 res = of_Range(ls);
		res.path = ls;
		return res;
	}
		

	private Double beneficio; //beneficio que tenemos
	private List<Variedad> variedades; //variedades que tenemos
	private List<Integer> solucion; //cantidad que cogemos de cada variedad

	// Ahora en la PI5
	private List<Integer> path;
	
	private SolucionEjercicio1() {
		beneficio = 0.;
		solucion = List2.empty();
		variedades = List2.empty();
	}
	private SolucionEjercicio1(List<Integer> ls) {
		beneficio  = 0.;
		solucion = List2.of();
		variedades = List2.empty();
		for(int i=0; i<ls.size(); i++) {
			if(ls.get(i)>0) {				
				Integer e = ls.get(i); //x[i]
				Double v = DatosEjercicio1.getBeneficio(i); //beneficio
				beneficio += v * e; //beneificio += beneficio[i] * x[i]
				variedades.add(DatosEjercicio1.variedades.get(i));
				solucion.add(e);
			}
		}
	}
	
	public static SolucionEjercicio1 empty() {
		return new SolucionEjercicio1();
	}

	public static SolucionEjercicio1 create(List<Integer> ls) {
		return new SolucionEjercicio1(ls);
	}

	@Override
	public String toString() {
		String res = String.format("Solucion = %s; Tamaño solucion = %d; beneficio total = %f;", solucion, solucion.size(), beneficio);
		return path==null? res: String.format("%s\nPath de la solucion: %s", res, path);		
	}
}
