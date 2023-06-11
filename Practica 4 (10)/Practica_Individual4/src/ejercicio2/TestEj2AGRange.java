package ejercicio2;

import java.util.List;
import java.util.Locale;

import _soluciones.SolucionEjercicio2;
import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agstopping.StoppingConditionFactory;


public class TestEj2AGRange {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		
		AlgoritmoAG.ELITISM_RATE  = 0.10;
		AlgoritmoAG.CROSSOVER_RATE = 0.95;
		AlgoritmoAG.MUTATION_RATE = 0.8;
		AlgoritmoAG.POPULATION_SIZE = 1000;
		
		StoppingConditionFactory.NUM_GENERATIONS = 1000;
		StoppingConditionFactory.stoppingConditionType = StoppingConditionFactory.StoppingConditionType.GenerationCount;
		
		for (int i = 1; i < 4; i++) {
			Ejercicio2AG p = new Ejercicio2AG("ficheros/Ejercicio2DatosEntrada" + i + ".txt");
			AlgoritmoAG<List<Integer>, SolucionEjercicio2> ap = AlgoritmoAG.of(p);
			ap.ejecuta();
			System.out.println("================================");
			System.out.println(ap.bestSolution());
			System.out.println("================================\n");
		}
		
	}
}
