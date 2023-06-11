package ejercicios.ejercicio3.manual;

import java.util.List;

import _datos.DatosEjercicio3;
import _datos.DatosEjercicio3.Trabajo;
import _soluciones.SolucionEjercicio3;
import us.lsi.common.List2;

public class Ejercicio3State {

	Ejercicio3Problem actual;
	Double acumulado;
	List<Integer> acciones;
	List<Ejercicio3Problem> anteriores;
	
	private Ejercicio3State(Ejercicio3Problem p, Double a, 
		List<Integer> ls1, List<Ejercicio3Problem> ls2) {
		// TODO Inicializar las propiedades individuales
		actual = p; 
		acumulado = a;
		acciones = ls1;
		anteriores =  ls2;
	}

	public static Ejercicio3State initial() {
		// TODO Crear el estado inicial
		return new Ejercicio3State(Ejercicio3Problem.initial(), 0.,List2.empty(),List2.empty());
	}

	public static Ejercicio3State of(Ejercicio3Problem prob, Double acum, List<Integer> lsa,
			List<Ejercicio3Problem> lsp) {
		return new Ejercicio3State(prob, acum, lsa, lsp);
	}

	public void forward(Integer a) {		
		// TODO Avanzar un estado segun la alternativa a
		acciones.add(a);
		anteriores.add(actual);
		acumulado = actual.calidadActual() * 1.0;
		actual = actual.neighbor(a);
	}

	public void back() {
		// TODO Retroceder al estado anterior
		Ejercicio3Problem prob_anterior = anteriores.get(anteriores.size()-1);
		Integer accion_anterior = acciones.get(anteriores.size()-1);
		
		actual = prob_anterior;
		acumulado = prob_anterior.calidadActual() * 1.0;
		acciones.remove(accion_anterior);
		anteriores.remove(prob_anterior);	
	}

	public List<Integer> alternativas() {
		// TODO Alternativas segun el actual
		return this.actual.actions();
	}

	public Double cota(Integer a) {
		// TODO Cota = acumulado + func(a, actual) + h(vecino(actual, a))
		Integer weight = DatosEjercicio3.trabajos.stream().map(Trabajo::calidad).max(Integer::compareTo).get();
//		System.out.println("dqdfqwdfwed" +weight);
		return this.acumulado + weight + actual.neighbor(a).heuristic();
	}

	public Boolean esSolucion() {
		// TODO Cuando todos los elementos del universo se han cubierto
		return actual.remainingInv().stream().allMatch(x -> x>=0) && actual.index() == DatosEjercicio3.getTrabajos() * DatosEjercicio3.getInvestigadores();
	}

	public Boolean esTerminal() {
		// TODO Cuando se han recorrido todos los Ejercicio3
		return this.esSolucion();
	}

	public SolucionEjercicio3 getSolucion() {
		// TODO Aprovechamos lo hecho en la PI4
		return SolucionEjercicio3.of_Range(acciones);
	}

}
