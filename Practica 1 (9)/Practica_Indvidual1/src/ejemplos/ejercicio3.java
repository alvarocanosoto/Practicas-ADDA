package ejemplos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import us.lsi.geometria.Punto2D;
import us.lsi.geometria.Punto2D.Cuadrante;
import us.lsi.streams.Stream2;

public class ejercicio3 {

	public static List<Punto2D> ejercicio3Iterativo(String fileA, String fileB) {
		Function<String,Punto2D> parsePunto = s -> {
			String[] s2 = s.split(",");
			return Punto2D.of(Double.valueOf(s2[0]), Double.valueOf(s2[1]));
		};

		Iterator<Punto2D> iterator1 = Stream2.file(fileA).map(parsePunto).toList().iterator();
		Iterator<Punto2D> iterator2 = Stream2.file(fileB).map(parsePunto).toList().iterator();
		List<Punto2D> lres = new ArrayList<>();
		Punto2D it1 = iterator1.hasNext()?iterator1.next():null;
		Punto2D it2 = iterator2.hasNext()?iterator2.next(): null;
		while(it1 != null || it2 != null){
			Punto2D it=null;
			if(it2==null || it1.compareTo(it2)<0){
				it = it1;
				it1 = iterator1.hasNext()?iterator1.next():null;
			} else {
				it = it2;
				it2 = iterator2.hasNext()?iterator2.next():null;
			}
			if( it.getCuadrante() == Cuadrante.PRIMER_CUADRANTE || it.getCuadrante()== Cuadrante.TERCER_CUADRANTE) lres.add(it);
		}

		return lres;
	}

	public static List<Punto2D> ejercicio3Recursivo(String fileA, String fileB){
		Function<String,Punto2D> parsePunto = s -> {
			String[] s2 = s.split(",");
			return Punto2D.of(Double.valueOf(s2[0]), Double.valueOf(s2[1]));
		};

		Iterator<Punto2D> iterator1 = Stream2.file(fileA).map(parsePunto).toList().iterator();
		Iterator<Punto2D> iterator2 = Stream2.file(fileB).map(parsePunto).toList().iterator();
		List<Punto2D> lres = new ArrayList<>();
		Punto2D it1 = iterator1.hasNext()?iterator1.next():null;
		Punto2D it2 = iterator2.hasNext()?iterator2.next(): null;
		return ejercicio3RecursivoAux(iterator1,iterator2, it1, it2,lres);
	}

	public static List<Punto2D> ejercicio3RecursivoAux(Iterator<Punto2D> iterator1, Iterator<Punto2D> iterator2, Punto2D it1, Punto2D it2 , List<Punto2D> lres){
		if(it1 != null || it2 != null){
			Punto2D it=null;
			if(it2==null || it1.compareTo(it2)<0){
				it = it1;
				it1 = iterator1.hasNext()?iterator1.next():null;
			} else {
				it = it2;
				it2 = iterator2.hasNext()?iterator2.next():null;
			}
			if( it.getCuadrante() == Cuadrante.PRIMER_CUADRANTE || it.getCuadrante()== Cuadrante.TERCER_CUADRANTE) {
				lres.add(it);
				lres = ejercicio3RecursivoAux(iterator1, iterator2, it1, it2, lres);
			} else {
				ejercicio3RecursivoAux(iterator1, iterator2, it1, it2, lres);
			}
		}

		return lres;
	}


	public record Tupla(Iterator<Punto2D> iterador1, Iterator<Punto2D> iterador2, Punto2D it1, Punto2D it2) {
		private static Tupla of(Iterator<Punto2D> iterador1, Iterator<Punto2D> iterador2, Punto2D it1, Punto2D it2) {
			return new Tupla(iterador1,iterador2,it1,it2);
		}

		private static Tupla first(Iterator<Punto2D> iterador1, Iterator<Punto2D> iterador2) {
			return of(iterador1,iterador2,iterador1.hasNext()?iterador1.next():null,
					iterador2.hasNext()?iterador2.next():null);
		}

		private Tupla nx() {
			Tupla res = this;
			if(it1!=null ||it2!=null) {
				if(it2==null || it1.compareTo(it2)<0) {
					res = Tupla.of(iterador1, iterador2, iterador1.hasNext()?iterador1.next():null, it2);
				}else if(it1==null || it2.compareTo(it1)<0) {
					res= Tupla.of(iterador1, iterador2, it1,iterador2.hasNext()?iterador2.next():null);	 
				}
			}
			return res;
		}

	}
	//Funcion Funcional
	public static List<Punto2D> ejemplo3Funcional (String fileA, String fileB){

		Function<String,Punto2D> parsePunto = s -> {
			String[] s2 = s.split(",");
			return Punto2D.of(Double.valueOf(s2[0]), Double.valueOf(s2[1]));
		}; 

		Iterator<Punto2D> iterator1 = Stream2.file(fileA).map(parsePunto).filter(punto -> punto.getCuadrante() == Cuadrante.PRIMER_CUADRANTE || punto.getCuadrante()== Cuadrante.TERCER_CUADRANTE).iterator();
		Iterator<Punto2D> iterator2 = Stream2.file(fileB).map(parsePunto).filter(punto -> punto.getCuadrante() == Cuadrante.PRIMER_CUADRANTE || punto.getCuadrante()== Cuadrante.TERCER_CUADRANTE).iterator();

		return Stream.iterate(Tupla.first(iterator1, iterator2), t-> t.nx())
				.takeWhile(t->t.it1()!=null && t.it2()!=null)
				.map(p-> (p.it1() != null && p.it2() != null)?p.it1().compareTo(p.it2())<0 ? p.it1():p.it2():p.it1()!=null?p.it1():p.it2())
				//.filter(p->p!=null)
				.collect(Collectors.toList());
	}


}
