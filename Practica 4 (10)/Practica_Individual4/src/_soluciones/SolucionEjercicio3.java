package _soluciones;

import java.util.List;
import java.util.stream.Collectors;

import _datos.DatosEjercicio3;
import _datos.DatosEjercicio3.Investigador;
import us.lsi.common.List2;

public class SolucionEjercicio3 {
	
	public static SolucionEjercicio3 of_Range(List<Integer> ls) {
		return new SolucionEjercicio3(ls);
	}

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
		//variables aux
		Integer numTrabajos = DatosEjercicio3.getTrabajos();
		Integer numInvestigadores = DatosEjercicio3.getInvestigadores();
		Integer numEspecialidades = DatosEjercicio3.getEspecialidades();

		calidad = 0;
		investigadores = DatosEjercicio3.investigadores;
		solucion = List2.empty();
		//para hacer cada  i0,...,in,  i0,...,in,  ...
		for (int i=0; i<numInvestigadores; i++) {
			solucion.add(List2.empty());
		}


		for (int j=0; j<numTrabajos; j++) {
			Integer trabajosValue = j * numInvestigadores;
			List<Integer> trabajoActual = ls.subList(trabajosValue, trabajosValue+numInvestigadores); //me quedo con el j que quiero
			//actualizo las horas que cada trabajador i le dedica al trabajo j
			for (int i=0; i<numInvestigadores; i++) {
				solucion.get(i).add(trabajoActual.get(i));
			}
			Boolean trabaja=true;
			for (int k=0; k<numEspecialidades; k++) {
				Integer suma=0;
				for (int i=0; i<numInvestigadores; i++) {
					suma += trabajoActual.get(i)*DatosEjercicio3.seleccionaEspecialidad(i, k); //sum(x[i,j], i in 0 .. n | seleccionaEspecialidad(i, k) = 1)
				}
				if (suma < DatosEjercicio3.diasNecesito(j, k)) {
					trabaja = false;//y[j] = 0
					k = numEspecialidades;
				}
			}
			
			if (trabaja) {//y[j] = 1
				calidad += DatosEjercicio3.getCalidad(j);
			}
		}
	}
	
	public static SolucionEjercicio3 empty() {
		return new SolucionEjercicio3();
	}


	@Override
	public String toString() {
		String s = investigadores.stream()
		        .map(i -> "INVESTIGADOR " + i.nombre() + ": " + i)
		        .collect(Collectors.joining("\n", "Reparto de horas:\n", "\n"));
		return String.format("%sSuma de las calidades de los trabajos realizados: %d", s, calidad);
	}
}
