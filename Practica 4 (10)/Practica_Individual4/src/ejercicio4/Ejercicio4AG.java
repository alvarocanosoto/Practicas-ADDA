package ejercicio4;

import java.util.List;

import _datos.DatosEjercicio4;
import _soluciones.SolucionEjercicio4;
import us.lsi.ag.SeqNormalData;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;

public class Ejercicio4AG implements SeqNormalData<SolucionEjercicio4> {

	public Ejercicio4AG(String linea) {
		DatosEjercicio4.iniDatos(linea);
	}

	@Override
	public ChromosomeType type() {
		// TODO Auto-generated method stub
		return ChromosomeType.Permutation;
	}

	@Override
	public Double fitnessFunction(List<Integer> value) {
		// TODO Auto-generated method stub
		//variables aux
		double goal = 0;
		double restriccion = 0;
		double fM = 0;
		double fmAux = 0;


		for (int i = 0; i < value.size(); i++) {
			if (i == 0) { //El reparto comienza desde una localización concreta (el almacén)
				if (DatosEjercicio4.existeArista(0, value.get(i))) {
					fmAux += DatosEjercicio4.getDistancia(0, value.get(i)); //Cada kilómetro recorrido tiene un coste de 1 céntimo
					goal += DatosEjercicio4.getBeneficio(value.get(i)) - fmAux; //Cada kilómetro recorrido tiene un coste de 1 céntimo
				}
				else { //si la arista no existe, la solucion es mala
					restriccion++;
				}
			} else {
				if (DatosEjercicio4.existeArista(value.get(i - 1), value.get(i))) {
					fmAux += DatosEjercicio4.getDistancia(value.get(i - 1), value.get(i));
					goal += DatosEjercicio4.getBeneficio(value.get(i)) - fmAux;
				} else { //si la arista no existe, la solucion es mala
					restriccion++;
				}
			}
		}


		if (value.get(value.size() - 1) != 0) { //si no vuelve al almacén no sirve
			restriccion = restriccion * 2;
		}

		//Fitness Maximo
		fmAux = 0.;
		for (int i = 0; i < value.size(); i++) {
			fmAux += DatosEjercicio4.getBeneficio(value.get(i)); //Maximizar beneficio
		}
		fM = Math.pow(fmAux, 2);


		return 0.0 + goal - fM * restriccion;
	}

	@Override
	public SolucionEjercicio4 solucion(List<Integer> value) {
		// TODO Auto-generated method stub
		return SolucionEjercicio4.of_Range(value);
	}

	@Override
	public Integer itemsNumber() {
		// TODO Auto-generated method stub
		return DatosEjercicio4.getNumVertices();
	}

}
