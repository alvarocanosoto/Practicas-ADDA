package _datos;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import us.lsi.common.Files2;

public class DatosEjercicio3 {
	
	public static List<Investigador> investigadores;
	public static record Investigador(String nombre, Integer capacidad, Integer especialidad) {
		public Investigador of(String nombre, Integer capacidad, Integer especialidad) {
			return new Investigador(nombre, capacidad, especialidad);
		}
		
		public static Investigador ofFormat(String linea) {
			String[] formato = linea.split(":");
			String nombre = formato[0];
			String segundaParte = formato[1].trim();
			String[] segundaPartePars = segundaParte.split(";");
			Integer capacidad = Integer.parseInt(segundaPartePars[0].split("=")[1].trim());
			Integer especialidad = Integer.parseInt(segundaPartePars[1].split("=")[1].trim());
			return new Investigador(nombre,capacidad, especialidad);
		}
		
	}
	
	public static List<Trabajo> trabajos;
	public static record Trabajo(String nombre, Integer calidad, Map<Integer, Integer> reparto) {
		public Trabajo of(String nombre, Integer calidad, Map<Integer, Integer> reparto) {
			return new Trabajo(nombre, calidad, reparto);
		}
		
		public static Trabajo ofFormat(String linea) {
			String[] formato = linea.split("->");
			String nombre = formato[0].trim();
			String segundaParte = formato[1].trim();
			String[] segundaPartePars = segundaParte.split(";");
			Integer calidad = Integer.parseInt(segundaPartePars[0].split("=")[1].trim());
			Map<Integer, Integer> reparto = Arrays.stream(segundaPartePars[1].split("=")[1].split(","))
			        .map(pair -> pair.split(":"))
			        .collect(Collectors.toMap(
			                keyValue -> Integer.parseInt(keyValue[0].replace("(", "").replace(")", "")),
			                keyValue -> Integer.parseInt(keyValue[1].replace("(", "").replace(")", ""))
			        ));
			return new Trabajo(nombre, calidad, reparto);
		}
	}
	
	
	public static void iniDatos(String fichero) {
		List<String> lineas = Files2.linesFromFile(fichero);
		investigadores = lineas.stream().filter(l -> l.startsWith("I")).map(x -> Investigador.ofFormat(x)).toList();
		trabajos = lineas.stream().filter(l -> l.startsWith("T")).map(x -> Trabajo.ofFormat(x)).toList();	
		toConsole();
	}

	//.......................................................
	//head section del .lsi
	
	public static Integer getTrabajos() {
		return trabajos.size();
	}

	public static Integer getInvestigadores() {
		return investigadores.size();
	}

	public static Integer getCapacidad(Integer i) {
		return investigadores.get(i).capacidad;
	}
	public static Integer getCalidad(Integer i) {
		return trabajos.get(i).calidad;
	}
	
	public static Integer getMaximoDias() {
		return investigadores.stream().map(Investigador::capacidad).reduce((a, b) -> a>b?a:b).get();
	}
	
//	public static Integer compruebaTrabajo(Integer i, Integer k) {
//		Integer checkInv = investigadores.get(i).especialidad();
//		Set<Integer> checkTra = trabajos.get(k).reparto().keySet();
//		return checkTra.contains(checkInv)?1:0;
//	}
	
	public static Integer diasNecesito(Integer j, Integer k) {
		return trabajos.get(j).reparto().get(k);
	}
	
	public static Integer getEspecialidades() {
		return investigadores.stream().map(Investigador::especialidad).distinct().collect(Collectors.toList()).size();
	}
	public static Integer totalTrabajo(Integer j) {
		return trabajos.get(j).reparto().values().stream().mapToInt(Integer::intValue).sum();
	}

	public static Integer seleccionaEspecialidad(Integer i, Integer k) {
		return investigadores.get(i).especialidad().equals(k)?1:0;		
	}
	//.......................................................
	public static List<Investigador> getListaInvestigadores(){
		return investigadores;
	}
	public static List<Trabajo> getListaTrabajos(){
		return trabajos;
	}
	
	public static void toConsole() {
		//String2.toConsole("Conjunto de Entrada Tipos: %s\nConjunto de Entrada Variedades: %d", tipos, variedades);	
		System.out.println(investigadores);
		System.out.println(trabajos);
	}	
	
	// Test de la lectura del fichero
	public static void main(String[] args) {
		iniDatos("ficheros/Ejercicio3DatosEntrada1.txt");
	}	
}
