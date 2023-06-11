package tests;
import java.util.List;
import java.util.stream.Collectors;

import ejemplos.Ejercicio3;
import us.lsi.common.Files2;
import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.Tree;


public class TestEjercicio3 {

		public static void main(String[] args) {

			System.out.println("/////////////////////////////////////////////////\n" 
					+ " \t\tEjercicio3: Arbol Biario\t\t\t  \n"
					+ "/////////////////////////////////////////////////\n");
			List<String> lineasFicheroBinario = Files2.streamFromFile("./ficheros/Ejercicio3DatosEntradaBinario.txt")
					.collect(Collectors.toList());
			System.out.println("Datos entrada Binario: " + lineasFicheroBinario);
			for (String linea : lineasFicheroBinario) {
				String[] element = linea.split("#");
				Character caracter = element[1].charAt(0);
				BinaryTree<Character> arbol = BinaryTree.parse(element[0], s -> s.charAt(0));
				System.out.println("Arbol Binario: " + arbol + " Caracter: " + caracter);
				System.out.println(Ejercicio3.ejercicio3BI(arbol, caracter));
			}
			
			System.out.println("/////////////////////////////////////////////////\n" 
					+ " \t\tEjercicio3: Arbol N-ario\t\t\t  \n"
					+ "/////////////////////////////////////////////////\n");
			List<String> lineasFicherNario = Files2.streamFromFile("./ficheros/Ejercicio3DatosEntradaNario.txt")
					.collect(Collectors.toList());
			System.out.println("Datos entrada Nario: " + lineasFicherNario);
			for (String linea : lineasFicherNario) {
				String[] element = linea.split("#");
				Character caracter = element[1].charAt(0);
				Tree<Character> arbol = Tree.parse(element[0], s -> s.charAt(0));
				System.out.println("Arbol Nario: " + arbol + " Caracter: " + caracter);
				System.out.println(Ejercicio3.ejercicio3Arbol(arbol, caracter));

			}
			
			
		}

	
}
