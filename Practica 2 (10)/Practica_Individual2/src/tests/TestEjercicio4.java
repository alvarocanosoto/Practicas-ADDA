package tests;

import java.util.List;
import java.util.stream.Collectors;

import ejemplos.Ejercicio4;
import us.lsi.common.Files2;
import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.Tree;

public class TestEjercicio4 {
	public static void main(String[] args) {
		System.out.println("/////////////////////////////////////////////////\n" 
				+ " \t\tEjercicio4: Arbol Biario\t\t\t  \n"
				+ "/////////////////////////////////////////////////\n");
		List<String> lineasFicheroBinario = Files2.streamFromFile("./ficheros/Ejercicio4DatosEntradaBinario.txt")
				.collect(Collectors.toList());
		System.out.println("Datos entrada Binario: " + lineasFicheroBinario);
		for (String linea : lineasFicheroBinario) {
			BinaryTree<String> arbol = BinaryTree.parse(linea);
			System.out.println("Arbol: " + arbol);
			System.out.println(Ejercicio4.ejercicio4BI(arbol));

		}
		
		System.out.println("/////////////////////////////////////////////////\n" 
				+ " \t\tEjercicio4: Arbol N-ario\t\t\t  \n"
				+ "/////////////////////////////////////////////////\n");
		List<String> lineasFicherNario = Files2.streamFromFile("./ficheros/Ejercicio4DatosEntradaNario.txt")
				.collect(Collectors.toList());
		System.out.println("Datos entrada Nario: " + lineasFicherNario);
		for (String linea : lineasFicherNario) {
			Tree<String> arbol = Tree.parse(linea);
			System.out.println("Arbol: " + arbol);
			System.out.println(Ejercicio4.ejercicio4Arbol(arbol));

		}
}
	
	
}
