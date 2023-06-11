package ejercicios.ejercicio4.manual;

import java.util.List;

import _datos.DatosEjercicio4;
import _soluciones.SolucionEjercicio4;
import us.lsi.common.List2;

public class Ejercicio4State {

	Ejercicio4Problem actual;
	Double acumulado;
	List<Integer> acciones;
	List<Ejercicio4Problem> anteriores;
	
	private Ejercicio4State(Ejercicio4Problem p, Double a, 
		List<Integer> ls1, List<Ejercicio4Problem> ls2) {
		// TODO Inicializar las propiedades individuales
		actual = p; 
		acumulado = a;
		acciones = ls1;
		anteriores =  ls2;
	}

	public static Ejercicio4State initial() {
		// TODO Crear el estado inicial
		return new Ejercicio4State(Ejercicio4Problem.initial(),0.,List2.empty(),List2.empty());
	}

	public static Ejercicio4State of(Ejercicio4Problem prob, Double acum, List<Integer> lsa,
			List<Ejercicio4Problem> lsp) {
		return new Ejercicio4State(prob, acum, lsa, lsp);
	}

	public void forward(Integer a) {		
		// TODO Avanzar un estado segun la alternativa a
		acciones.add(a);
		anteriores.add(actual);
	
		acumulado += DatosEjercicio4.getBeneficio(actual.index());
		actual = actual.neighbor(a);
	}

	public void back() {
		// TODO Retroceder al estado anterior
		Integer indice_ultimo_problema = anteriores.size()-1;
		Ejercicio4Problem prob_anterior = anteriores.get(indice_ultimo_problema);
		
		Integer indice_de_la_decision = prob_anterior.index();

		Integer accion_anterior = acciones.get(indice_ultimo_problema);
		
		acumulado =  DatosEjercicio4.getBeneficio(indice_de_la_decision);
		actual = prob_anterior;
		acciones.remove(accion_anterior);
		anteriores.remove(prob_anterior);
	}

	public List<Integer> alternativas() {
		// TODO Alternativas segun el actual
		return this.actual.actions();
	}

	public Double cota(Integer a) {
		// TODO Cota = acumulado + func(a, actual) + h(vecino(actual, a))
		Double weight = DatosEjercicio4.getBeneficio(a);
		return this.acumulado + weight + actual.neighbor(a).heuristic();
	}

	public Boolean esSolucion() {
		// TODO Cuando todos los elementos del universo se han cubierto

		return actual.remainingClientes().isEmpty() && actual.clientesVisitados().get(actual.clientesVisitados().size()-1)==0;
	}

	public Boolean esTerminal() {
		// TODO Cuando se han recorrido todos los Ejercicio4
		return actual.clientesVisitados().size()==DatosEjercicio4.getNumVertices()+1;
	}

	public SolucionEjercicio4 getSolucion() {
		// TODO Aprovechamos lo hecho en la PI4
		return SolucionEjercicio4.of_Range(acciones);
	}

}
