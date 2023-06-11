package ejemplos;

import java.util.ArrayList;
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

public class Ejercicio3 {

	//	Dados un árbol binario de caracteres y un carácter, diseñe un algoritmo que devuelva
	//	una lista con todas las cadenas que se forman desde la raíz a una hoja no vacía, excluyendo
	//	aquellas cadenas que contengan dicho carácter. Proporcione una solución también para
	//	árboles n-arios.

	/*   EJERCICIO 3  Binario*/
	public static List<String> ejercicio3BI(BinaryTree<Character> tree, Character caracter) {	

		return ejercicio3BI_Aux(tree, caracter, new ArrayList<>(), "");
	}

	public static List<String> ejercicio3BI_Aux(BinaryTree<Character> tree, Character caracter, List<String> res, String acum) {
		Function<String, Boolean> contiene = elem -> 
		{										 
			return elem.contains(caracter.toString());
		};
		return switch(tree) {
		case BEmpty<Character> t ->{
			res.remove(acum);
			yield res;
		}

		case BLeaf<Character> t ->{
			Character elemento_actual = t.label();
			acum = acum + elemento_actual;
			if(!contiene.apply(acum)) {
				res.add(acum);
				yield res;
			}else {
				res.remove(acum);
				yield res;
			}

		}

		case BTree<Character> t -> {
			Character elemento_actual = t.label();
			acum = acum + elemento_actual;
			if(!contiene.apply(acum)) {
				String acum2 = acum;
				ejercicio3BI_Aux(t.left(), caracter, res, acum);
				ejercicio3BI_Aux(t.right(), caracter, res, acum2);
			}
			yield res;
		}
		};
	}
	
	/*   EJERCICIO 3  Arbol*/

	public static List<String> ejercicio3Arbol(Tree<Character> tree, Character caracter) {	

		return ejercicio3Arbol_Aux(tree, caracter, new ArrayList<>(), "");
	}

	public static List<String> ejercicio3Arbol_Aux(Tree<Character> tree, Character caracter,  List<String> res, String acum) {
		Function<String, Boolean> contiene = elem -> 
		{										 
			return elem.contains(caracter.toString());
		};
		return switch(tree) {
		case TEmpty<Character> t ->{
			res.remove(acum);
			yield res;
		}

		case TLeaf<Character> t ->{
			Character elemento_actual = t.label();
			acum = acum + elemento_actual;
			if(!contiene.apply(acum)) {
				res.add(acum);
				yield res;
			}else {
				res.remove(acum);
				yield res;
			}
		}

		case TNary<Character> t -> {
			Character elemento_actual = t.label();
			acum = acum + elemento_actual;
			if(!contiene.apply(acum)) {
				String acum2 = acum;
				t.elements().forEach(tc -> ejercicio3Arbol_Aux(tc, caracter, res, acum2));
			}
			yield res;
		}
		};
	}
	

}
