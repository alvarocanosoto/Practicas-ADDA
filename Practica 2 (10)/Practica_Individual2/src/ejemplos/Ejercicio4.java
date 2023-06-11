package ejemplos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.BinaryTree.BEmpty;
import us.lsi.tiposrecursivos.BinaryTree.BLeaf;
import us.lsi.tiposrecursivos.BinaryTree.BTree;
import us.lsi.tiposrecursivos.Tree;
import us.lsi.tiposrecursivos.Tree.TEmpty;
import us.lsi.tiposrecursivos.Tree.TLeaf;
import us.lsi.tiposrecursivos.Tree.TNary;

public class Ejercicio4 {

//	Dado un árbol binario de cadena de caracteres, diseñe un algoritmo que devuelva cierto
//	si se cumple que, para todo nodo, el número total de vocales contenidas en el subárbol
//	izquierdo es igual al del subárbol derecho. Proporcione una solución también para árboles
//	n-arios.
		
	record Tupla (Boolean cumple, Integer num_voc) {
		public static Tupla of(Boolean cumple, Integer num_voc) {
			return new Tupla(cumple, num_voc);
		}
	}
	
	/*   EJERCICIO 4  Binario*/
	public static Boolean ejercicio4BI(BinaryTree<String> tree) {	

		return ejercicio4BI_Aux(tree).cumple();
	}

	public static Tupla ejercicio4BI_Aux(BinaryTree<String> tree) {
		List<Character> vocales = Arrays.asList('a', 'e', 'i', 'o', 'u');
		Function<String, Integer> numVocales = elem -> 
		{		
			Integer num = 0;
			
			for (int i = 0; i < elem.length(); i++) {
				if(vocales.contains(elem.charAt(i))) num++; 
	        }
			return num;
		};
		Tupla res;
		return switch(tree) {
		case BEmpty<String> t -> Tupla.of(true, 0);

		case BLeaf<String> t -> Tupla.of(true, numVocales.apply(t.label()));

		case BTree<String> t -> {
			Tupla izq = ejercicio4BI_Aux (t.left());
			Tupla der = ejercicio4BI_Aux (t.right());
			res = Tupla.of(izq.cumple()&&der.cumple()&&(izq.num_voc()==der.num_voc()), izq.num_voc()+der.num_voc()+numVocales.apply(t.label()));
			yield res;
		}
		};
	}
	
	/*   EJERCICIO 4  Arbol*/
	public static Boolean ejercicio4Arbol(Tree<String> tree) {	
		Tupla res = Tupla.of(true, 0) ;
		return ejercicio4Arbol_Aux(tree, res).cumple();
	}

	public static Tupla ejercicio4Arbol_Aux(Tree<String> tree, Tupla res) {
		List<Character> vocales = Arrays.asList('a', 'e', 'i', 'o', 'u');
		Function<String, Integer> numVocales = elem -> 
		{		
			Integer num = 0;
			
			for (int i = 0; i < elem.length(); i++) {
				if(vocales.contains(elem.charAt(i))) num++;   
	        }
			return num;
		};
		
		return switch(tree) {
		case TEmpty<String> t -> Tupla.of(true, 0);

		case TLeaf<String> t -> Tupla.of(true, numVocales.apply(t.label()));

		case TNary<String> t -> {
			List<Tupla> listaTuplas = new ArrayList<>();
			t.elements().forEach(tc -> {
				listaTuplas.add(ejercicio4Arbol_Aux(tc, res));
			});
			
			Boolean igual = listaTuplas.stream().distinct() .count() <= 1;
			Integer vocalesTotal = listaTuplas.stream().mapToInt(l -> l.num_voc()).sum(); //lista de tuplas la transformo a lista de vocales y la sumo
			
			yield Tupla.of(igual, vocalesTotal);
		}
		};
	}
}
