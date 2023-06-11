package _soluciones;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import _datos.DatosEjercicio2;
import _datos.DatosEjercicio2.Curso;
import us.lsi.common.List2;

public class SolucionEjercicio2 {
	
	public static SolucionEjercicio2 of_Range(List<Integer> ls) {
		return new SolucionEjercicio2(ls);
	}

	private double costoTotal;
    private List<Curso> cursos;

	private SolucionEjercicio2() {
		costoTotal = 0.;
		cursos = List2.empty();
	}
	private SolucionEjercicio2(List<Integer> ls) {
		costoTotal = 0;
		cursos = new ArrayList<>();
		

		for (int i = 0; i < ls.size(); i++) {
			if (ls.get(i) > 0) {
				Curso curso = DatosEjercicio2.getListaCursos().get(i);
				costoTotal += curso.coste();
				cursos.add(curso);
			}
		}
	}
	
	public static SolucionEjercicio2 empty() {
		return new SolucionEjercicio2();
	}

	public static SolucionEjercicio2 create(List<Integer> ls) {
		return new SolucionEjercicio2(ls);
	}


	@Override
	public String toString() {
		String s = cursos.stream().map(e -> "S" + e.id())
				.collect(Collectors.joining(", ", "Cursos elegidos: {", "}\n"));
		return String.format("%sCoste Total: %.1f", s, costoTotal);
	}
}
