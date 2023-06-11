package _soluciones;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.jgrapht.GraphPath;

import _datos.DatosEjercicio3;
import _datos.DatosEjercicio3.Investigador;
import _datos.DatosEjercicio3.Trabajo;
import ejercicios.ejercicio3.Ejercicio3Edge;
import ejercicios.ejercicio3.Ejercicio3Vertex;
import us.lsi.common.List2;
import us.lsi.common.Map2;

public class SolucionEjercicio3 implements Comparable<SolucionEjercicio3>{
	
	public static SolucionEjercicio3 of_Range(List<Integer> ls) {
		return new SolucionEjercicio3(ls);
	}
	// Ahora en la PI5
		public static SolucionEjercicio3 of(GraphPath<Ejercicio3Vertex, Ejercicio3Edge> path) {
			List<Integer> ls = path.getEdgeList().stream().map(e -> e.action()).toList();
			SolucionEjercicio3 res = of_Range(ls);
			res.path = ls;
			return res;
		}
			
		// Ahora en la PI5
		private List<Integer> path;
	

	private Integer calidad;
	private List<Investigador> investigadores;
	private List<List<Integer>> solucion;

	//List(0,...,n-1,  n,...2n-1,  ...)
	//     i0,...,in,  i0,...,in,  ...
	//       j0			j1         ...
	
	private SolucionEjercicio3() {
		calidad = 0;
		investigadores = List2.empty();
		solucion = List2.empty();
	}
	private SolucionEjercicio3(List<Integer> ls) {
		Integer numTrabajos = DatosEjercicio3.getTrabajos();
		Integer numInvestigadores = DatosEjercicio3.getInvestigadores();
		Integer nxm = numTrabajos * numInvestigadores;
		List<List<Integer>> listasDivididas = List2.empty();
		List<List<Integer>> listaReparto = List2.empty();
		Map<Integer, List<Integer>> espeInv = Map2.empty();
		for(int i = 0; i < DatosEjercicio3.getEspecialidades();i++) {
			List<Integer> listaN = List2.empty();
			for(int j=0;j < listasDivididas.size();j++) {
				if(DatosEjercicio3.investigadores.get(j).especialidad()==i) {
					listaN.add(j);
				}
			}
			espeInv.put(i, listaN);
		}

		// Divide la lista en m listas de n elementos cada una
		for (int i = 0; i < numInvestigadores; i++) {
			int desde = i * numTrabajos;
			int hasta = (i + 1) * numTrabajos;
			List<Integer> sublista = ls.subList(desde, hasta);
			listasDivididas.add(sublista);
		}
		for(Trabajo t:DatosEjercicio3.trabajos) {
			listaReparto.addAll(List2.of(t.reparto().values().stream().toList()));
		}
		
		Integer m = DatosEjercicio3.getTrabajos();
		for(int i = 0; i < nxm;i++) {
			Integer especialidadActual = DatosEjercicio3.investigadores.get(i/m).especialidad();
			Integer trabajoActual = i%m;
			Integer nuevaCap = listaReparto.get(trabajoActual).get(especialidadActual) - ls.get(i);
			List<Integer> lista_aux = List2.setElement(listaReparto.get(trabajoActual), especialidadActual, nuevaCap);
			listaReparto.set(trabajoActual, lista_aux);
		}
		solucion = listaReparto;
		calidad = IntStream.range(0, listaReparto.size()).boxed().filter(x -> listaReparto.get(x).stream().allMatch(y -> y == 0)).map(x -> DatosEjercicio3.trabajos.get(x).calidad()).reduce((a,b) -> a =a+b).orElse(0);
		investigadores = DatosEjercicio3.investigadores;
		
	}
	
	public static SolucionEjercicio3 empty() {
		return new SolucionEjercicio3();
	}


	@Override
	public String toString() {
//		System.out.println("reparto de horas: " + solucion);
		String s = investigadores.stream()
		        .map(i -> "INVESTIGADOR " + i.nombre() + ": " + i)
		        .collect(Collectors.joining("\n", "Reparto de horas:\n", "\n"));
		return String.format("%sSuma de las calidades de los trabajos realizados: %d", s, calidad);
	}
	@Override
	public int compareTo(SolucionEjercicio3 o) {
		// TODO Auto-generated method stub
		return 0;
	}
}
