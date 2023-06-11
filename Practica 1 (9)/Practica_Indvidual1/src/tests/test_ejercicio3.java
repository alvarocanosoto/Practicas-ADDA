package tests;

import ejemplos.ejercicio3;

public class test_ejercicio3 {

	public static void main(String[] args) {

		String fileA = "C:\\Users\\Alvaro\\eclipse-workspace\\Practica_Indvidual1\\ficheros\\PI1Ej3DatosEntrada1A.txt";
		String fileB = "C:\\Users\\Alvaro\\eclipse-workspace\\Practica_Indvidual1\\ficheros\\PI1Ej3DatosEntrada1B.txt";

		System.out.println("##################################################");
		System.out.println("1) Solucion Iterativa:\n" + ejercicio3.ejercicio3Iterativo(fileA, fileB));
		System.out.println("1) Solucion Recursiva Final:\n" + ejercicio3.ejercicio3Recursivo(fileA, fileB));
		System.out.println("1) Solucion Funcional:\n" + ejercicio3.ejemplo3Funcional(fileA, fileB));
		System.out.println("##################################################" + "\n");
		for(int i = 2; i < 4; i++) {
			fileA = "C:\\Users\\Alvaro\\eclipse-workspace\\Practica_Indvidual1\\ficheros\\PI1Ej3DatosEntrada" + i + "A.txt";
			fileB = "C:\\Users\\Alvaro\\eclipse-workspace\\Practica_Indvidual1\\ficheros\\PI1Ej3DatosEntrada" + i + "B.txt";		
			System.out.println("##################################################");
			System.out.println("1) Solucion Iterativa: " + ejercicio3.ejercicio3Iterativo(fileB, fileA));
			System.out.println("1) Solucion Recursiva Final: " + ejercicio3.ejercicio3Recursivo(fileB, fileA));
			System.out.println("1) Solucion Funcional: " + ejercicio3.ejemplo3Funcional(fileA, fileB));
			System.out.println("##################################################" + "\n");

		}

	}

}
