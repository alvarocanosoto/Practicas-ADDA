package tests;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import ejemplos.Ejercicio1;
import us.lsi.common.Pair;
import us.lsi.common.Trio;
import us.lsi.curvefitting.DataCurveFitting;
import utils.GraficosAjuste;
import utils.Resultados;
import utils.TipoAjuste;

public class TestEjercicio1 {

	public static void main(String[] args) {

		System.out.println("/////////////////////////////////////////////////\n" 
				+ " \t\tEjercicio_1\t\t\t  \n"
				+ "/////////////////////////////////////////////////\n");
		System.out.println("Sol. Iterativa con Double: " + Ejercicio1.factIterDouble(6));
		System.out.println("Sol. Recursiva con Double: " + Ejercicio1.factRecDouble(6) + "\n");

		System.out.println("Sol. Iterativa con BigInteger: " + Ejercicio1.factIterBigInteger(6));
		System.out.println("Sol. Recursiva con BigInteger: " + Ejercicio1.factRecBigInteger(6));

		System.out.println("\n/////////////////////////////////////////////////////////\n"
				+ " \t\t\tEjercicio_1\t\t \n" 
				+ " \tGenerar ficheros y representar graficas\t \n"
				+ "/////////////////////////////////////////////////////////\n");

//		generaFicherosTiempoEjecucionDouble();
//		generaFicherosTiempoEjecucionBigInteger();
//		muestraGraficas();
	}

	private static Integer nMin = 100; // n mínimo para el cálculo de potencia
	private static Integer nMaxDouble= 10000; // n máximo para el cálculo del factorial con DOUBLE
	private static Integer nMaxBigInteger = 5000; // n máximo para el cálculo del factorial con BIGINTEGER
	private static Integer numSizes = 100; // número de problemas (número de potencias distintas a calcular)
	private static Integer numMediciones = 10; // número de mediciones de tiempo de cada caso (número de experimentos)
	private static Integer numIter = 50; // número de iteraciones para cada medición de tiempo
	private static Integer numIterWarmup = 1000; // número de iteraciones para warmup

	// Trios de métodos a probar con su tipo de ajuste y etiqueta para el nombre de
	// los ficheros
	private static List<Trio<Function<Integer, Double>, TipoAjuste, String>> metodosDouble = List.of(
			Trio.of(Ejercicio1::factIterDouble, TipoAjuste.POWERANB, "factIterDouble(lineal)"),
			Trio.of(Ejercicio1::factRecDouble, TipoAjuste.POWERANB, "factRecDouble(lineal)"));

	private static List<Trio<Function<Integer, BigInteger>, TipoAjuste, String>> metodosBigInteger = List.of(
			Trio.of(Ejercicio1::factIterBigInteger, TipoAjuste.POWERANB, "factIterBigInteger(lineal)"),
			Trio.of(Ejercicio1::factRecBigInteger, TipoAjuste.POWERANB, "factRecBigInteger(lineal)"));

	public static void generaFicherosTiempoEjecucionDouble() {
		for (int i = 0; i < metodosDouble.size(); i++) {
			String ficheroSalida = String.format("ficheros/Tiempos%s.csv", metodosDouble.get(i).third());
			testTiemposEjecucionDouble(nMin, nMaxDouble, metodosDouble.get(i).first(), ficheroSalida);
		}
	}

	public static void generaFicherosTiempoEjecucionBigInteger() {
		for (int i = 0; i < metodosBigInteger.size(); i++) {
			String ficheroSalida = String.format("ficheros/Tiempos%s.csv", metodosBigInteger.get(i).third());

			testTiemposEjecucionBigInteger(nMin, nMaxBigInteger, metodosBigInteger.get(i).first(), ficheroSalida);
		}
	}

	public static void testTiemposEjecucionDouble(Integer nMin, Integer nMax,
			Function<Integer, Double> funcion,
			String ficheroTiempos
			) {

		Map<Problema, Double> tiempos = new HashMap<Problema,Double>();
		Integer nMed = numMediciones; 
		for (int iter=0; iter<nMed; iter++) {
			for (int i=0; i<numSizes; i++) {
				Double r = Double.valueOf(nMax-nMin)/(numSizes-1);	
				Integer tam = (Integer.MAX_VALUE/nMax > i) 
						? nMin + i*(nMax-nMin)/(numSizes-1)
								: nMin + (int) (r*i) ;
				Problema p = Problema.of(tam);
				warmupDouble(funcion, 10);
				Integer nIter = numIter;
				Double[] res = new Double[nIter];
				Long t0 = System.nanoTime();
				for (int z=0; z<nIter; z++) {
					res[z] = funcion.apply(tam);
				}
				Long t1 = System.nanoTime();
				actualizaTiempos(tiempos, p, Double.valueOf(t1-t0)/nIter);
			}

		}

		Resultados.toFile(tiempos.entrySet().stream()
				.map(x->TResultD.of(x.getKey().tam(), 
						x.getValue()))
				.map(TResultD::toString),
				ficheroTiempos, true);

	}

	public static void testTiemposEjecucionBigInteger(Integer nMin, Integer nMax,
			Function<Integer, BigInteger> funcion,
			String ficheroTiempos
			) {

		Map<Problema, Double> tiempos = new HashMap<Problema,Double>();
		Integer nMed = numMediciones; 
		for (int iter=0; iter<nMed; iter++) {
			for (int i=0; i<numSizes; i++) {
				Double r = Double.valueOf(nMax-nMin)/(numSizes-1);
				//				double r = (nMax-nMin)/(numSizes-1);
				Integer tam = (Integer.MAX_VALUE/nMax > i) 
						? nMin + i*(nMax-nMin)/(numSizes-1)
								: nMin + (int) (r*i) ;
				Problema p = Problema.of(tam);
				warmupBigInteger(funcion, 10);
				Integer nIter = numIter;
				BigInteger[] res = new BigInteger[nIter];
				Long t0 = System.nanoTime();
				for (int z=0; z<nIter; z++) {
					res[z] = funcion.apply(tam);
				}
				Long t1 = System.nanoTime();
				actualizaTiempos(tiempos, p, Double.valueOf(t1-t0)/nIter);
			}

		}

		Resultados.toFile(tiempos.entrySet().stream()
				.map(x->TResultD.of(x.getKey().tam(), 
						x.getValue()))
				.map(TResultD::toString),
				ficheroTiempos, true);

	}

	private static void actualizaTiempos(Map<Problema, Double> tiempos, Problema p, double d) {
		if (!tiempos.containsKey(p)) {
			tiempos.put(p, d);
		} else if (tiempos.get(p) > d) {
			tiempos.put(p, d);
		}
	}

	private static void warmupDouble(Function<Integer, Double> pot, Integer n) {
		for (int i=0; i<numIterWarmup; i++) {
			pot.apply(n);
		}
	}

	private static void warmupBigInteger(Function<Integer, BigInteger> pot, Integer n) {
		for (int i=0; i<numIterWarmup; i++) {
			pot.apply(n);
		}
	}

	private record TResultD(Integer tam, Double t) {
		public static TResultD of(Integer tam, Double t){
			return new TResultD(tam, t);
		}

		public String toString() {
			return String.format("%d,%.0f", tam, t);
		}
	}

	private record Problema(Integer tam) {
		public static Problema of(Integer tam){
			return new Problema(tam);
		}
	}



	public static void muestraGraficas() {
		System.out.println("a*n^b*(ln n)^c + d");
		List<String> ficherosSalida = new ArrayList<>();
		List<String> labels = new ArrayList<>();

		for (int i = 0; i < metodosDouble.size(); i++) {

			String ficheroSalida = String.format("ficheros/Tiempos%s.csv", metodosDouble.get(i).third());
			ficherosSalida.add(ficheroSalida);
			String label = metodosDouble.get(i).third();
			System.out.println(label);

			TipoAjuste tipoAjuste = metodosDouble.get(i).second();
			GraficosAjuste.show(ficheroSalida, tipoAjuste, label);

			Pair<Function<Double, Double>, String> parCurve = GraficosAjuste
					.fitCurve(DataCurveFitting.points(ficheroSalida), tipoAjuste);
			String ajusteString = parCurve.second();
			labels.add(String.format("%s     %s", label, ajusteString));

		}

		for (int i = 0; i < metodosBigInteger.size(); i++) {

			String ficheroSalida = String.format("ficheros/Tiempos%s.csv", metodosBigInteger.get(i).third());
			ficherosSalida.add(ficheroSalida);
			String label = metodosBigInteger.get(i).third();
			System.out.println(label);

			TipoAjuste tipoAjuste = metodosBigInteger.get(i).second();
			GraficosAjuste.show(ficheroSalida, tipoAjuste, label);

			Pair<Function<Double, Double>, String> parCurve = GraficosAjuste
					.fitCurve(DataCurveFitting.points(ficheroSalida), tipoAjuste);
			String ajusteString = parCurve.second();
			labels.add(String.format("%s     %s", label, ajusteString));

		}

		GraficosAjuste.showCombined("Factorial", ficherosSalida, labels);
	}
}