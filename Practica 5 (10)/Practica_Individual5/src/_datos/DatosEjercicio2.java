package _datos;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import us.lsi.common.Files2;

public class DatosEjercicio2 {
	
	private static int id_aux = 0;
	public static Integer maxCentros;
	public static List<Curso> cursos;
	public record Curso(Integer id, Set<Integer> tematicas, Double coste, Integer centro) {
		public Curso of(Integer id, Set<Integer> tematicas, Double coste, Integer centro) {
			return new Curso(id, tematicas, coste, centro);
		}
		
		public static Curso ofFormat(String linea) {
			String[] formato = linea.split(":");
			Integer id = id_aux++;
			Set<Integer> tematicas = auxiliar(formato[0]);
			Double coste = Double.parseDouble(formato[1]);
		    Integer centro = Integer.parseInt(formato[2]);
		    return new Curso(id,tematicas, coste, centro);
		}
		
	}
	
	public static Set<Integer> auxiliar(String formato){
		Set<Integer> res = new HashSet<>();
		String[] aux = formato.replace("{", "").replace("}", "").split(",");
		for (String auxi : aux) {
			res.add(Integer.parseInt(auxi));
		}
		return res;
	}
	
	public static void iniDatos(String fichero) {
		String[] v = Files2.linesFromFile(fichero).get(0).split(":");
		List<String> lineas = Files2.linesFromFile(fichero);
		maxCentros = lineas.stream().filter(l -> l.contains("Max_Centros")).map(x ->{
			String[] parts = x.split("=");
			return Integer.parseInt(parts[1].trim());
		}).findFirst().orElse(null);
		cursos = lineas.stream().filter(l -> l.startsWith("{")).map(x -> Curso.ofFormat(x)).toList();
		//toConsole();
		System.out.println(cursos);
		System.out.println("Max_Centros = " + maxCentros);
	}

	//.......................................................
	//head section del .lsi
	public static Integer getCursos() { //nº de cursos
		return cursos.size();
	}
	public static Integer getTematicas() { //nº de tematicas
		Set<Integer> aux = new HashSet<>();
		cursos.stream().map(Curso::tematicas).forEach(x -> aux.addAll(x));
//		System.out.println("Hay " + (int) aux.stream().distinct().count() + " tematicas");
//		System.out.println(aux.stream().distinct().collect(Collectors.toList()));
		return (int) aux.stream().distinct().count();
	}
	public static List<Integer> getListaTematicas(){
		return cursos.stream().flatMap(x -> x.tematicas().stream()).distinct().collect(Collectors.toList());
	}
	public static Integer getCentros() { //nº de centros
		return getListaCentros().size();
	}
	public static Integer getCentrosDiferentes() { //maxCentros
		return maxCentros;
	}
	public static Double getPrecioInscripcion(Integer i) { //coste de un curso
		return cursos.stream().filter(c -> c.id()==i).findFirst().map(Curso::coste).get();
	}
	public static Integer seleccionaTematica(Integer i, Integer j) { //devuelve 1 si se selecciona la tematica j
		return cursos.get(i).tematicas().contains(getListaTematicas().get(j))?1:0;
	}
	public static List<Integer> getListaCentros() {
		return cursos.stream().map(Curso::centro).distinct().collect(Collectors.toList());
	}
	public static Integer seleccionaCentro(Integer i, Integer j) { //devuelve 1 si sel centro j ofrece el curso i
		return cursos.get(i).centro().equals(getListaCentros().get(j))?1:0;
	}
	
	public static double maximoCoste() {
		return cursos.stream().map(Curso::coste).reduce((a, b) -> a>b?a:b).get();
	}
	//.......................................................
	
	public static List<Curso> getListaCursos(){
		return cursos;
	}
	
//	public static void toConsole() {
//		String2.toConsole("Conjunto de Entrada: %s\nSuma objetivo: %d", numeros, SUMA);	
//	}	
	
	// Test de la lectura del fichero
	public static void main(String[] args) {
		iniDatos("ficheros/Ejercicio2DatosEntrada1.txt");
//		System.out.println(getListaCentros());
	}	
}
