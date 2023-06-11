package ejercicios.ejercicio3.manual;

import java.util.Set;

import _soluciones.SolucionEjercicio3;
import us.lsi.common.Set2;

public class Ejercicio3BT {

	private static Double mejorValor;
	private static Ejercicio3State estado;
	private static Set<SolucionEjercicio3> soluciones;
	
	public static void search() {
		soluciones = Set2.newTreeSet();
		mejorValor = Double.MIN_VALUE; // Estamos maximizando
		estado = Ejercicio3State.initial();
		bt_search();
	}

	private static void bt_search() {
		if (estado.esSolucion()) {
//			System.out.println("1");
			Double valorObtenido = estado.acumulado;
			if (valorObtenido > mejorValor) {  // Estamos maximizando
//				System.out.println("2");
				mejorValor = valorObtenido;
				soluciones.add(estado.getSolucion());
			}
		} else if(!estado.esTerminal()){
//			System.out.println("3");
			for (Integer a: estado.alternativas()) {
				if (estado.cota(a) >= mejorValor) {  // Estamos maximizando
//					System.out.println("4");
					estado.forward(a);
					bt_search();
					estado.back();
				}
			}
		}
	}

	public static Set<SolucionEjercicio3> getSoluciones() {
		return soluciones;
	}

}
