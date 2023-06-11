package ejemplos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import us.lsi.common.List2;

public class ejercicio4 {
	public static String ejemplo4RecSinMemoria(Integer a, Integer b, Integer c) {
		String res = null;
		if (a < 2 && b <= 2 || c < 2) {
			res = "(" + a + "+" + b + "+" +  c + ")";
		}else if(a < 3 || b < 3 && c <= 3) {
			res = "(" + c + "-" + b + "-" +  a + ")";
		}else if(b%a == 0 && (a%2 == 0 || b%3 == 0)){
			res = "(" + ejemplo4RecSinMemoria(a-1, b/a, c-1) + "*" + ejemplo4RecSinMemoria(a-2, b/2, c/2) + ")";
		}else {
			res = "(" + ejemplo4RecSinMemoria(a/2, b-2, c/2) + "/" + ejemplo4RecSinMemoria(a/3, b-1, c/3) + ")";
		}
		return res;
	}

	public static String ejemplo4RecConMemoria(Integer a, Integer b, Integer c) {
		Map<List<Integer>, String> mapa = new HashMap<>();
		return ejemplo4RecConMemoriaAux(a, b, c, mapa);
	}


	public static String ejemplo4RecConMemoriaAux(Integer a, Integer b, Integer c, Map<List<Integer>, String> mapa) {
		String res = null;
		if(mapa.containsKey(List2.of(a,b,c))) {
			res = mapa.get(List2.of(a,b,c));
		} else {
			if (a < 2 && b <= 2 || c < 2) {
				res = "(" + a + "+" + b + "+" +  c + ")";
			}else if(a < 3 || b < 3 && c <= 3) {
				res = "(" + c + "-" + b + "-" +  a + ")";
			}else if(b%a == 0 && (a%2 == 0 || b%3 == 0)){
				res = "(" + ejemplo4RecConMemoriaAux(a-1, b/a, c-1, mapa) + "*" + ejemplo4RecConMemoriaAux(a-2, b/2, c/2, mapa) + ")";
			}else {
				res = "(" + ejemplo4RecConMemoriaAux(a/2, b-2, c/2, mapa) + "/" + ejemplo4RecConMemoriaAux(a/3, b-1, c/3, mapa) + ")";
			}
			mapa.put(List2.of(a,b,c), res);
		}

		return res;
	}

	public static String ejemplo4Iterativo(Integer a, Integer b, Integer c) {
		String res = null;
		Map<List<Integer>, String> mapa = new HashMap<>();
		//int i = 0, j = 0, k = 0;
		for(int i = 0; i <= a; i++) {
			for(int j = 0; j <= b; j++) {
				for(int k = 0; k <= c; k++) {
					if (i < 2 && j <= 2 || k < 2) {
						res = "(" + i + "+" + j + "+" +  k + ")";
					}else if(i < 3 || j < 3 && k <= 3) {
						res = "(" + k + "-" + j + "-" +  i + ")";
					}else if(j%i == 0 && (a%2 == 0 || j%3 == 0)){
						res = "(" + mapa.get(List2.of(i-1, j/i, k-1)) + "*" + mapa.get(List2.of(i-2, j/2, k/2)) + ")";
					}else {
						res = "(" + mapa.get(List2.of(i/2, j-2, k/2)) + "/" + mapa.get(List2.of(i/3, j-1, k/3)) + ")";
					}
					mapa.put(List2.of(i,j,k), res);
				}
			}
		}
		return res;
	}
}
