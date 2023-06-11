package ejemplos;

import java.math.BigInteger;

public class Ejercicio1 {

	public static BigInteger factIterBigInteger(Integer n) {
		BigInteger factorial = BigInteger.ONE;
		int i = n;
		while(i>0) {
			factorial = factorial.multiply(BigInteger.valueOf(i));
			i--;
		}

		return factorial;
	}

	public static BigInteger factRecBigInteger(Integer n) {
		BigInteger factorial = BigInteger.ONE;

		if(n>0) {
			factorial = factRecBigInteger(n-1).multiply(BigInteger.valueOf(n));
		}

		return factorial;
	}

	public static Double factIterDouble(Integer n) {
		Double x = 1.;
		Double y = 1.;
		while(x <= n){
			y = y * x;
			x++;
		}
		return y;
	}

	public static Double factRecDouble(Integer n) {
		Double factorial = 1.;

		if(n>0) {
			factorial = factRecDouble(n-1)*n;
		}

		return factorial;
	}

}
