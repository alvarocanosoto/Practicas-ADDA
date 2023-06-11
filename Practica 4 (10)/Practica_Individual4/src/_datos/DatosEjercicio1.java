package _datos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import us.lsi.common.Files2;
import us.lsi.common.String2;

public class DatosEjercicio1 {
	
	public static List<Tipo> tipos;
	public static record Tipo(String Nombre_Tipo, Integer kgdisponibles, Integer id) {
		public Tipo of(String Nombre_Tipo, Integer kgdisponibles, Integer id) {
			return new Tipo(Nombre_Tipo, kgdisponibles, id);
		}
		
		public static Tipo ofFormat(String linea) {
			String[] formato = linea.split(":");
			String Nombre_Tipo = formato[0];
			Integer kgdisponibles = Integer.parseInt(formato[1].replace("kgdisponibles=", "").replace(";", "").trim());
			Integer id = Integer.parseInt(formato[0].replace("C", ""));
			return new Tipo(Nombre_Tipo,kgdisponibles, id);
		}
		
	}
	
	public static List<Variedad> variedades;
	public static record Variedad(String nombre, Integer id, Double beneficio, Map<String, Double> porcentaje) {
		public Variedad of(String nombre, Integer id, Double beneficio, Map<String, Double> porcentaje) {
			return new Variedad(nombre, id, beneficio, porcentaje);
		}
		
		public static Variedad ofFormat(String linea) {
			String[] formato = linea.split("->");
			String nombre = formato[0].trim();
			String segundaParte = formato[1].trim();
			String[] segundaPartePars = segundaParte.split(";");
			Double beneficio = Double.parseDouble(segundaPartePars[0].split("=")[1].trim());
			Map<String, Double> porcentaje = Arrays.stream(segundaPartePars[1].split("=")[1].split(","))
			        .map(pair -> pair.split(":"))
			        .collect(Collectors.toMap(
			                keyValue -> keyValue[0].replace("(", "").replace(")", ""),
			                keyValue -> Double.parseDouble(keyValue[1].replace("(", "").replace(")", ""))
			        ));

			Integer id = null;
			return new Variedad(nombre, id, beneficio, porcentaje);
		}
	}
	
	
	public static void iniDatos(String fichero) {
		List<String> lineas = Files2.linesFromFile(fichero);
		tipos = lineas.stream().filter(l -> l.startsWith("C")).map(x -> Tipo.ofFormat(x)).toList();
		variedades = lineas.stream().filter(l -> l.startsWith("P")).map(x -> Variedad.ofFormat(x)).toList();	
		toConsole();
	}

	//.......................................................
	//head section del .lsi
	
	public static Integer getTipos() {
		return tipos.size();
	}

	public static Integer getVariedades() {
		return variedades.size();
	}
	
	public static Double getBeneficio(Integer i) {
		return variedades.get(i).beneficio;
	}
	public static Double getPorcentajeVariedad(Integer i, Integer j) {
		Set<String> setTipos = variedades.get(i).porcentaje.keySet();
		String aux = tipos.stream().filter(x -> tipos.indexOf(x) == j).map(Tipo::Nombre_Tipo).findFirst().orElse(null);
		return setTipos.contains(aux)?variedades.get(i).porcentaje().get(aux):0.;
	}
	public static Integer getKgDisponibles(Integer i) {
		return tipos.get(i).kgdisponibles;
	}
	public static Integer getKgDisponiblesMax() {
		return tipos.stream().map(Tipo::kgdisponibles).reduce((a, b) -> a > b?a:b).get();
	}
	
	public static Double getBounds(Integer i, Integer j) {
		Tipo tipo = tipos.get(i);
		Double porcentaje = getPorcentajeVariedad(i, j);
		return tipo.kgdisponibles()/porcentaje;
	}
	
	//.......................................................
	public static List<Tipo> getListaTipos(){
		return tipos;
	}
	public static List<Variedad> getListaVariedades(){
		return variedades;
	}
	
	public static void toConsole() {
		//String2.toConsole("Conjunto de Entrada Tipos: %s\nConjunto de Entrada Variedades: %d", tipos, variedades);	
		System.out.println(tipos);
		System.out.println(variedades);
	}	
	
	// Test de la lectura del fichero
	public static void main(String[] args) {
		iniDatos("ficheros/Ejercicio1DatosEntrada1.txt");
	}	
}
