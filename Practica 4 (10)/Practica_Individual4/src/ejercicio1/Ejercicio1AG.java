package ejercicio1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import _datos.DatosEjercicio1;
import _datos.DatosEjercicio1.Tipo;
import _datos.DatosEjercicio1.Variedad;
import _soluciones.SolucionEjercicio1;
import us.lsi.ag.ValuesInRangeData;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;
import us.lsi.bufete.datos.TipoSolucion;
import us.lsi.common.List2;

public class Ejercicio1AG implements ValuesInRangeData<Integer, SolucionEjercicio1> {

	public Ejercicio1AG(String linea) {
		DatosEjercicio1.iniDatos(linea);
	}
	
	@Override
	public Integer size() {
		// TODO Auto-generated method stub
		return DatosEjercicio1.getVariedades();
	}

	@Override
	public ChromosomeType type() {
		// TODO Auto-generated method stub
		return ChromosomeType.Range;
	}

	@Override
	public Double fitnessFunction(List<Integer> cromosoma_variedad) {
		// TODO Auto-generated method stub
		Double goal = 0.;
		for (int i = 0; i < cromosoma_variedad.size(); i++) {
			goal+= cromosoma_variedad.get(i) * DatosEjercicio1.getBeneficio(i);
		}
				
		//Fitness Maximo
		Double fM = 0.;
		for (int i = 0; i < cromosoma_variedad.size(); i++) {
			fM+= this.max(i)*2*DatosEjercicio1.getBeneficio(i);
		}

		//Restricciones
		Integer restriccion = 0;
				
		for (int cafe = 0; cafe < DatosEjercicio1.tipos.size(); cafe++) {
			Boolean compruebo = false;
			Double suma = 0.;
			for(int variedad = 0; variedad < cromosoma_variedad.size();variedad++) {
				suma  += cromosoma_variedad.get(variedad) * DatosEjercicio1.getPorcentajeVariedad(variedad, cafe);
			}
			compruebo = suma <= DatosEjercicio1.tipos.get(cafe).kgdisponibles();
			if(!compruebo) {
				System.out.println("");
			}
			restriccion+=compruebo?0:1;
		}

		
		return 0.0 + goal - (restriccion * fM);
	}

	@Override
	public SolucionEjercicio1 solucion(List<Integer> value) {
		System.out.println(value); //esto es solo para hacer pruebas
		return SolucionEjercicio1.create(value);
	}

	@Override
	public Integer max(Integer i) {
		// TODO Auto-generated method stub
		List<Double> aux = List2.empty();
		for (Tipo v : DatosEjercicio1.tipos) {
			Integer cafe_i = DatosEjercicio1.tipos.indexOf(v);
			Integer kg_cafe_i = DatosEjercicio1.getKgDisponibles(cafe_i);
			Double porcentaje_cafe_i_var_j = DatosEjercicio1.getPorcentajeVariedad(i, cafe_i);
			aux.add((kg_cafe_i/porcentaje_cafe_i_var_j)+1);
		}
		return aux.stream().reduce((a,b) -> a<b?a:b).get().intValue();
	}

	@Override
	public Integer min(Integer i) {
		// TODO Auto-generated method stub
		return 0;
	}

}
