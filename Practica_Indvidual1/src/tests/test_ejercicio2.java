package tests;

import java.util.List;

import ejemplos.ejercicio2;
import us.lsi.common.Files2;

public class test_ejercicio2 {

	public static void main(String[] args) {

		String fichero = "C:\\\\Users\\\\Alvaro\\\\eclipse-workspace\\\\Practica_Indvidual1\\\\ficheros\\\\PI1Ej2DatosEntrada.txt";
		List<String> lineas = Files2.linesFromFile(fichero);
		System.out.println("Datos entrada: " + lineas);
		for (String linea : lineas) {
			String[] parsea = linea.split(",");
			Integer a = Integer.parseInt(parsea[0]);
			Integer b = Integer.parseInt(parsea[1]);
			String s = parsea[2];

			System.out.println("Solucion Iterativo: " + ejercicio2.ejercicio2Iterativo(a, b, s));
			System.out.println("Solucion Recursivo No Final: " + ejercicio2.ejercicio2RecNoFinal(a, b, s));
			System.out.println("Solucion Recursivo Final: " + ejercicio2.ejercicio2RecFinal(a, b, s));
			System.out.println("Solucion funcional: " + ejercicio2.ejercicio2Funcional(a, b, s));
			System.out.println("##################################################" + "\n");
		}
	}
}
