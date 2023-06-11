package ejemplos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ejercicio1 {

	private static record EnteroCadena(Integer a, String s) { //los records se usan para la representación de tuplas
		public static EnteroCadena of(Integer a, String s) {
			return new EnteroCadena (a, s);
		}
	}


	public static Map<Integer,List<String>> ejercicio1Funcional (Integer varA, String varB, Integer varC, String
			varD, Integer varE) {
		UnaryOperator<EnteroCadena> nx = elem -> //The UnaryOperator takes one argument, and returns a result of the same type of its arguments.
		{										 //public interface UnaryOperator<T> extends Function<T, T>{}
			//Function<EnteroCadena, EnteroCadena> == UnaryOperator<EnteroCadena>

			return EnteroCadena.of(elem.a()+2,		 //Devuelvo una tupla EnteroCadena (a + 2, ...)
					elem.a()%3==0?							 
							elem.s()+elem.a().toString():			 //Si a es multiplo de 3 -> Devuelvo EnteroCadena[a + 2, "s+a"]
								elem.s().substring(elem.a()%elem.s().length())); //substring(a mod (Tamaño de s)). Ej: EnteroCadena[a=5, s=pera] devuelve EnteroCadena[a=7, s=era]
			//Si no -> Devuelvo EnteroCadena[a + 2, "s.substring(a mod (Tamaño de s))"]
		};	//ej: "unhappy".substring(2) returns "happy" Lo que le paso por parámetros es la posición en la que empieza
		return Stream.iterate(EnteroCadena.of(varA,varB), elem -> elem.a() < varC, nx) //aquí elem vale EnteroCadena[a + 2, "s+a"] o EnteroCadena[a + 2, "s.substring(a mod (Tamaño de s))"]
				//iterate(seed,hasNext,next)
				//seed the initial element -> EnteroCadena.of(varA,varB)
				//hasNext a predicate to apply to elements to determine when the stream must terminate. -> mientras que a menor que c
				//next a function to be applied to the previous element to produce a new element -> UnaryOperator<EnteroCadena> nx
				.map(elem -> elem.s()+varD) //EnteroCadena[a + 2, "s+ varD"] OJO: s es s actualizada (s+a) o (s.substring(a mod (Tamaño de s)))
				.filter(nom -> nom.length() < varE) //nom = "s+ varD" -> Me quedo solo con los que su tamaño es menor a varE
				.collect(Collectors.groupingBy(String::length));//agrupo los EnteroCadena[a, s] en un map donde las keys es s.length
	}

	public static Map<Integer,List<String>> ejercicio1Iterativo (Integer varA, String varB, Integer varC, String
			varD, Integer varE) {
		EnteroCadena elem = EnteroCadena.of(varA,varB);
		Map<Integer,List<String>> res = new HashMap<Integer, List<String>>();

		while(elem.a<varC) {
			String cadena = elem.s() + varD;
			if(cadena.length()< varE) {
				if (res.containsKey(cadena.length())) {
					res.get(cadena.length()).add(cadena);
				} else {
					List<String> lista = new ArrayList<>();
					lista.add(cadena);
					res.put(cadena.length(), lista);
				}
			}

			if(elem.a()%3==0) elem = EnteroCadena.of(elem.a() + 2, elem.s() +elem.a().toString());
			else elem = EnteroCadena.of(elem.a() + 2, elem.s().substring(elem.a()%elem.s().length()));



		}	
		return res;
	}


	public static Map<Integer,List<String>> ejercicio1RecursivoFinal (Integer varA, String varB, Integer varC, String
			varD, Integer varE){
		Map<Integer,List<String>> res = new HashMap<Integer, List<String>>();
		return ejercicio1RecursivoAux(res, varA, varB, varC, varD, varE);

	}

	public static Map<Integer,List<String>> ejercicio1RecursivoAux (Map<Integer,List<String>> res,Integer varA, String varB, Integer varC, String
			varD, Integer varE){
		EnteroCadena elem = EnteroCadena.of(varA,varB);
		if(elem.a<varC) {
			String cadena = elem.s() + varD;

			if(cadena.length()< varE) {
				if (res.containsKey(cadena.length())) {
					res.get(cadena.length()).add(cadena);
				} else {
					List<String> lista = new ArrayList<>();
					lista.add(cadena);
					res.put(cadena.length(), lista);
				}
			}

			if(elem.a()%3==0) ejercicio1RecursivoAux(res, elem.a() + 2, elem.s() +elem.a().toString(), varC, varD, varE);
			else ejercicio1RecursivoAux(res, elem.a() + 2, elem.s().substring(elem.a()%elem.s().length()), varC, varD, varE);
		}	
		return res;
	}


}
