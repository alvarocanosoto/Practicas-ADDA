package tests;

import java.util.List;

import ejemplos.ejercicio1;
import us.lsi.common.Files2;

public class test_ejercicio1 {

	public static void main(String[] args) {

		String fichero = "C:\\Users\\Alvaro\\eclipse-workspace\\Practica_Indvidual1\\ficheros\\PI1Ej1DatosEntrada.txt";
		List<String> lineas = Files2.linesFromFile(fichero);
		System.out.println("Datos entrada: " + lineas);
		for (String linea : lineas) {
			String[] parsea = linea.split(",");
			Integer varA = Integer.parseInt(parsea[0]);
			String varB = parsea[1];
			Integer varC = Integer.parseInt(parsea[2]);
			String varD = parsea[3];
			Integer varE = Integer.parseInt(parsea[4]);


			System.out.println("Solucion funcional: " + ejercicio1.ejercicio1Funcional(varA, varB, varC, varD, varE));
			System.out.println("Solucion Iterativo: " + ejercicio1.ejercicio1Iterativo(varA, varB, varC, varD, varE));
			System.out.println("Solucion Recursivo Final: " + ejercicio1.ejercicio1RecursivoFinal(varA, varB, varC, varD, varE));
			System.out.println("##################################################" + "\n");
		}


	}

}
