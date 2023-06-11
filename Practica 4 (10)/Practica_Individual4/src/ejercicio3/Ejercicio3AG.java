package ejercicio3;

import java.util.List;

import _datos.DatosEjercicio2;
import _datos.DatosEjercicio3;
import _datos.DatosEjercicio3.Investigador;
import _soluciones.SolucionEjercicio3;
import us.lsi.ag.ValuesInRangeData;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;

public class Ejercicio3AG implements ValuesInRangeData<Integer, SolucionEjercicio3> {

	public Ejercicio3AG(String linea) {
		DatosEjercicio3.iniDatos(linea);
	}
	
	@Override
	public Integer size() {
		// TODO Auto-generated method stub
		return DatosEjercicio3.getTrabajos() * DatosEjercicio3.getInvestigadores(); //List(0,...,n-1,  n,...2n-1,  ...)
																					//     i0,...,in,  i0,...,in,  ...
																					//       j0			j1         ...
																					// para acceder a cada j hago la division
																					// para acceder a cada i hago el módulo
	}

	@Override
	public ChromosomeType type() {
		// TODO Auto-generated method stub
		return ChromosomeType.Range;
	}

	@Override
	public Double fitnessFunction(List<Integer> value) {
		// TODO Auto-generated method stub

		//variables aux
		Integer numTrabajos = DatosEjercicio3.getTrabajos();
		Integer numInvestigadores = DatosEjercicio3.getInvestigadores();
		Integer numEspecialidades = DatosEjercicio3.getEspecialidades();
		Integer capacidadUso = 0;
		Integer restriccion1 = 0;
		Integer restriccion2 = 0;

		
		//		max sum(getCalidad(j) y[j], j in 0 .. m)
		Double goal = 0.;		
				
		//Fitness Maximo
		Double fM = 0.;
		Double fmAux = 0.;
		for (int i = 0; i < numTrabajos; i++) {
			fmAux+= DatosEjercicio3.getCalidad(i);
		}
		
		fM = Math.pow(fmAux, 2);
		
		//sum(x[i,j], j in 0 .. m) <= getCapacidad(i), i in 0 .. n

		for (int i=0; i<numInvestigadores; i++) {
			capacidadUso=0; //comienzo dedicando 0 horas
			for (int invValue=i; invValue<value.size(); invValue+=numInvestigadores) { //Itero en la lista n*m el investigador i en cada uno de los trabajos
				capacidadUso += value.get(invValue);								   //voy actualizando las horas que le dedica este investigador i
			}		
			Investigador investigadorActual = DatosEjercicio3.investigadores.get(i);   
			if (capacidadUso > investigadorActual.capacidad()) restriccion1 += capacidadUso-investigadorActual.capacidad(); //voy sumando lo lejos que me quedo
		}
		
		//sum(x[i,j], i in 0 .. n | seleccionaEspecialidad(i, k) = 1) - diasNecesito(j, k) y[j] = 0, j in 0 .. m, k in 0 .. e
		/*
		 * for(int j=0...)
		 * 		for(int k = 0...)
		 * 			for(int i = 0...)
		 * 				diasUso = sum(x[i,j], i in 0 .. n | seleccionaEspecialidad(i, k) = 1)
		 * 		diasUso - diasNecesito(j, k) y[j] = 0
		 * 		
		 */
		
		
		
		for (int j=0; j<numTrabajos; j++) {
			Integer trabajosValue = j*numInvestigadores;//j0,j1,...,jm en values
			List<Integer> trab = value.subList(trabajosValue, trabajosValue+numInvestigadores);
			Boolean trabaja=true;
			for (int k=0; k<numEspecialidades; k++) {
				Integer diasUso=0;
				for (int i=0; i<numInvestigadores; i++) { //sum(x[i,j], i in 0 .. n | seleccionaEspecialidad(i, k) = 1)
					diasUso += trab.get(i)*DatosEjercicio3.seleccionaEspecialidad(i, k);
				}
				
				if (diasUso != DatosEjercicio3.diasNecesito(j, k)) {
					trabaja = false; //y[j] = 0
					restriccion2 += Math.abs(diasUso - DatosEjercicio3.diasNecesito(j, k)); //voy sumando lo lejos que me quedo
				}
			}
			if (trabaja) { //y[j] = 1
				goal += DatosEjercicio3.getCalidad(j);
			}
		}
		
		return 0.0 - goal - (restriccion1 * fM) - (restriccion2 * fM) ;
	}

	@Override
	public SolucionEjercicio3 solucion(List<Integer> value) {
		System.out.println(value); //esto es solo para hacer pruebas
		return SolucionEjercicio3.of_Range(value);
	}

	@Override
	public Integer max(Integer i) {
		// TODO Auto-generated method stub
		Integer i_aux = i%DatosEjercicio3.getInvestigadores(); //para cada trabajo j, veo el investigador i que más horas dedica
		return DatosEjercicio3.getCapacidad(i_aux)+1;
	}

	@Override
	public Integer min(Integer i) {
		// TODO Auto-generated method stub
		return 0;
	}

}
