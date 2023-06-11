package tests;

import java.util.List;

import ejemplos.ejercicio4;
import us.lsi.common.Files2;

public class test_ejercicio4 {

	public static void main(String[] args) {
		String fichero = "C:\\\\Users\\\\Alvaro\\\\eclipse-workspace\\\\Practica_Indvidual1\\\\ficheros\\\\PI1Ej4DatosEntrada.txt";
		List<String> lineas = Files2.linesFromFile(fichero);
		System.out.println("Datos entrada: " + lineas);
		for (String linea : lineas) {
			String[] parsea = linea.split(",");
			Integer a = Integer.parseInt(parsea[0]);
			Integer b = Integer.parseInt(parsea[1]);
			Integer c = Integer.parseInt(parsea[2]);	

			System.out.println("Solucion Iterativo: " + ejercicio4.ejemplo4Iterativo(a, b, c));
			System.out.println("Solucion. Recursivo Sin Memoria: " + ejercicio4.ejemplo4RecSinMemoria(a, b, c));
			System.out.println("Solucion Recursivo Con Memoria: " + ejercicio4.ejemplo4RecConMemoria(a, b, c));
			System.out.println("##################################################" + "\n");
		}

	}

}
