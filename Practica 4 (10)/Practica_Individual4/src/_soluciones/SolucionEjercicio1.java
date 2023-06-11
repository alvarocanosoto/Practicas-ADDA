package _soluciones;

import java.util.List;

import _datos.DatosEjercicio1;
import _datos.DatosEjercicio1.Variedad;
import us.lsi.common.List2;

public class SolucionEjercicio1 {
	
	public static SolucionEjercicio1 of_Range(List<Integer> ls) {
		return new SolucionEjercicio1(ls);
	}

	private Double beneficio; //beneficio que tenemos
	private List<Variedad> variedades; //variedades que tenemos
	private List<Integer> solucion; //cantidad que cogemos de cada variedad

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
//		int error = Math.abs(DatosEjercicio1.getSuma() - suma);
//		String e = error<1? "": String.format("Error = %d", error);
		return String.format("Solucion = %s; Tamaño solucion = %d; beneficio total = %f;", solucion, solucion.size(), beneficio);
	}
}
