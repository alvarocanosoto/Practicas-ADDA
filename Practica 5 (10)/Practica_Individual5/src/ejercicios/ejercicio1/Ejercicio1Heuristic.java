package ejercicios.ejercicio1;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import _datos.DatosEjercicio1;
import _datos.DatosEjercicio1.Tipo;
import _datos.DatosEjercicio1.Variedad;
import us.lsi.common.List2;

public class Ejercicio1Heuristic {
	
	// Se explica en practicas.
	public static Double heuristic(	Ejercicio1Vertex v1, 
									Predicate<Ejercicio1Vertex> goal,
									Ejercicio1Vertex v2) 
	{
		//multiplicar el beneficio maximo por la cantidad que puedo coger de esa variedad
		Double res = 0.;
		List<Variedad> variedades = DatosEjercicio1.variedades;
		for(Variedad v : variedades) {
			Double resAux = v.beneficio()*cantidadDisponible(v1, v);
			if(resAux>res) res = resAux;
		}
		return res;
		
	}
	
	
	public static Integer cantidadDisponible(Ejercicio1Vertex v1, Variedad v) {
		List<Integer> disponibles = List2.empty();	//aqui almaceno los Kg que tengo disponibles para cada tipo
		for (Map.Entry<String, Double> entry : v.porcentaje().entrySet()) {
			String tipo = entry.getKey();
			Tipo tipoNombre = DatosEjercicio1.tipos.stream().filter(x -> x.Nombre_Tipo().equals(tipo)).findFirst().get();
			Integer i = DatosEjercicio1.tipos.indexOf(tipoNombre); //indice del tipo en el que estoy

			Double porcentajeCojo = entry.getValue();
			Integer disponible = 0;
			while(v1.remaining().get(i)>=(porcentajeCojo*(disponible + 1))) {
				disponible++;
			}
			
			disponibles.add(disponible);	//añado la cantidad disponible para este tipo
			
		}
		Integer disponible = disponibles.stream().min(Integer::compare).get();	//me quedo con la menor de ellas... esta será la mayor cantidad
																				//que puedo coger de esta variedad

		return disponible;	
	}
}
