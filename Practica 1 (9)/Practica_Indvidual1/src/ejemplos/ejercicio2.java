package ejemplos;

import java.util.stream.Stream;


public class ejercicio2 {
	//Recursivo no final
	public static Integer ejercicio2RecNoFinal(Integer a, Integer b, String s){
		Integer r;
		if(s.length() == 0) 
			r = a * a + b * b;
		else if(a < 2 || b < 2)
			r = s.length() + a + b;
		else if(a%s.length()<b%s.length())
			r = a + b + ejercicio2RecNoFinal(a-1,b/2,s.substring(a%s.length(),b%s.length()));
		else
			r = a * b + ejercicio2RecNoFinal(a/2,b-1,s.substring(b%s.length(),a%s.length()));
		return r;
	}

	//Recursivo final
	public static Integer ejercicio2RecFinal(Integer a, Integer b, String s){
		Integer ba = 0;
		return ejercicio2RecFinal(ba, a, b, s);

	}

	private static Integer ejercicio2RecFinal(Integer ba, Integer a, Integer b, String s){
		Integer r;
		if(s.length() == 0) 
			r = ba + a * a + b * b;
		else if(a < 2 || b < 2)
			r = ba + s.length() + a + b;
		else if(a%s.length()<b%s.length())
			r = ejercicio2RecFinal(ba + a + b , a-1,b/2,s.substring(a%s.length(),b%s.length()));
		else
			r = ejercicio2RecFinal(ba + a * b , a/2,b-1,s.substring(b%s.length(),a%s.length()));
		return r;
	}

	//Iterativo
	public static Integer ejercicio2Iterativo(Integer a, Integer b, String s){
		Integer ba = 0;
		while(!(s.length() == 0)) {
			boolean sale = false;
			while(!(a < 2 || b < 2)) {
				while(!(a%s.length()<b%s.length())) {
					ba = ba + a * b;
					s = s.substring(b%s.length(),a%s.length());
					a = a/2;
					b = b-1;
					sale = true;
					break;
				}
				if(sale) break;
				ba = ba + a + b;
				s = s.substring(a%s.length(),b%s.length());
				a = a-1;
				b = b/2;
				sale = true;
				break;
			}
			if(!sale) return ba + s.length() + a + b;

		}
		return ba + a * a + b * b;
	}


	private static record Tupla(Integer ba, Integer a, Integer b, String s) { //los records se usan para la representación de tuplas
		public static Tupla of(Integer ba, Integer a, Integer b, String s) {
			return new Tupla (ba, a, b, s);
		}

		public static Tupla first(Integer ba, Integer a, Integer b, String s) {
			return of (0, a, b, s);
		}

		public Tupla next() {
			if ((a % s.length()) < (b % s.length())) return of((a + b) + ba, a - 1, b / 2, s.substring(a % s.length(), b % s.length()) );
			else return of((a * b) + ba,a / 2, b - 1, s.substring(b % s.length(), a % s.length()) );
		}

	}

	public static Integer ejercicio2Funcional(Integer a, Integer b, String s){
		Tupla t = Stream.iterate(Tupla.first(0, a, b, s), e -> e.next()) 
				.filter(( e -> e.s.length() == 0 || (e.a < 2 || e.b < 2))).findFirst().get();

		return t.s.length() == 0? t.a * t.a + t.b * t.b + t.ba : t.a < 2 || t.b < 2? t.s.length() + t.a + t.b + t.ba: null;
	}


}
