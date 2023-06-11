package ejercicio2;

import java.util.ArrayList;
import java.util.List;

import _datos.DatosEjercicio2;
import _soluciones.SolucionEjercicio2;
import us.lsi.ag.ValuesInRangeData;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;
import us.lsi.common.List2;

public class Ejercicio2AG implements ValuesInRangeData<Integer, SolucionEjercicio2> {

	public Ejercicio2AG(String linea) {
		DatosEjercicio2.iniDatos(linea);
	}
	
	@Override
	public Integer size() {
		// TODO Auto-generated method stub
		return DatosEjercicio2.getCursos();
	}

	@Override
	public ChromosomeType type() {
		// TODO Auto-generated method stub
		return ChromosomeType.Range;
	}

	@Override
	public Double fitnessFunction(List<Integer> value) {
		// TODO Auto-generated method stub
		Double goal = 0.;
		for (int i=0; i<value.size(); i++) {
			goal += value.get(i) * DatosEjercicio2.cursos.get(i).coste();
		}
		
		//Fitness Maximo
		Double fM = 0.;
		for (int i=0; i<value.size(); i++) {
			fM += DatosEjercicio2.cursos.get(i).coste() * DatosEjercicio2.maximoCoste()*2;
		}
		
		
		//Restricciones
		Integer restriccion1 = 0;
		Integer restriccion2 = 0;
	
		List<Integer> tematicas = List2.ofTam(0, DatosEjercicio2.getTematicas());
//		System.out.println(tematicas);
		for (int j = 0; j < DatosEjercicio2.getTematicas(); j++) {	
			for (int i = 0; i < value.size(); i++) {
				if(DatosEjercicio2.seleccionaTematica(i, j) * value.get(i) == 1) {
					tematicas.set(j, 1);
					break;
				}
			}
			/*
			 * Recorro las tematicas
			 * Dentro de una tematica, compruebo todos los cursos
			 * si se selecciona el curso y ese curso tiene esa tematica, añado 1
			 * si toda la lista esta rellena de 1, entonces se cubren todas las tematicas
			 */
		}

		restriccion1 = tematicas.stream().allMatch(x->x == 1)?0:1;
		
		Integer maxCentros = DatosEjercicio2.getCentrosDiferentes();
		List<Integer> centros = new ArrayList<>();
		for (int i = 0; i < value.size(); i++) {
			if(value.get(i) == 1) {
				centros.add(DatosEjercicio2.getListaCursos().get(i).centro());
			}
			/*
			 * Recorro los cursos y almaceno los centros en una lista
			 * compruebo cuantos centros diferentes hay y devuelvo 1 si
			 * hay más de maxCentros y 0 en caso contrario.
			 */
		}
		restriccion2 = centros.stream().distinct().count()<=maxCentros?0:1;
		
		return 0.0 - goal - (restriccion1 * fM) - (restriccion2 * fM);
	}

	@Override
	public SolucionEjercicio2 solucion(List<Integer> value) {
		System.out.println(value); //esto es solo para hacer pruebas
		
		return SolucionEjercicio2.create(value);
	}

	@Override
	public Integer max(Integer i) {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public Integer min(Integer i) {
		// TODO Auto-generated method stub
		return 0;
	}

}
