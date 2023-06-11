package ejercicios;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.interfaces.VertexCoverAlgorithm.VertexCover;
import org.jgrapht.alg.vertexcover.GreedyVCImpl;

import datos.Personas;
import datos.Rela;
import datos.Relacion;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;

import us.lsi.graphs.views.SubGraphView;

public class Ejercicio1 {
	//	Obtenga una vista del grafo que sólo incluya las personas cuyos padres aparecen
	//	en el grafo, y ambos han nacido en la misma ciudad y en el mismo año. Muestre
	//	el grafo configurando su apariencia de forma que se resalten los vértices y las
	//	aristas de la vista.
	public static void apartadoA(String file, Graph<Personas, Relacion> g, Predicate<Personas> pv, Predicate<Relacion> pa, String nombreVista) {
		Graph<Personas, Relacion> vista = SubGraphView.of(g, pv, pa);

		String fileRes = "resultados/ejercicio1/" + file + nombreVista + ".gv";
		GraphColors.toDot(g, fileRes,
				v->v.nombre(),e-> "",
				v-> GraphColors.colorIf(Color.blue, vista.vertexSet().contains(v)),
				e->  GraphColors.colorIf(Color.black, vista.edgeSet().contains(e))
				);
		System.out.println("Se ha generado " + fileRes);

	}

	//	Implemente un algoritmo que dada una persona devuelva un conjunto con todos
	//	sus ancestros que aparecen en el grafo. Muestre el grafo configurando su
	//	apariencia de forma que se resalte la persona de un color y sus ancestros de otro.	
	public static List<Personas> apartadoB_Aux(Graph<Personas, Relacion> g,Personas persona, List<Personas> acum){
		if(Graphs.vertexHasPredecessors(g, persona)) {
			for(Personas p : Graphs.predecessorListOf(g, persona)) {
				acum.add(p);
				apartadoB_Aux(g, p, acum);
			}
		}
		return acum;
	}

	private static Color asignaColor(Personas v, List<Personas> ls,Personas persona) {
		return ls.contains(v)? Color.blue : v == persona? Color.red : Color.black;
	}

	public static void apartadoB(String file, Graph<Personas, Relacion> g, String nombrePersona, String nombreVista) {
		Personas persona = g.vertexSet().stream().filter(v -> v.nombre().equals(nombrePersona)).findFirst().orElse(null);
		List<Personas> res = apartadoB_Aux(g, persona, new ArrayList<>());

		System.out.println(res);

		String fileRes = "resultados/ejercicio1/" + file + nombreVista + ".gv";
		GraphColors.toDot(g, fileRes,
				v->v.nombre(),e-> e.hijo().toString(),
				v-> GraphColors.color(asignaColor(v, res, persona)),
				e-> GraphColors.color(Color.black)
				);

		System.out.println("Se ha generado " + fileRes);


	}

	//		Implemente un algoritmo que dadas dos personas devuelva un valor entre los
	//		posibles del enumerado {Hermanos, Primos, Otros} en función de si son
	//		hermanos, primos hermanos, o ninguna de las dos cosas. Tenga en cuenta que 2
	//		personas son hermanas en caso de que tengan al padre o a la madre en común, y
	//		primas en caso de tener al menos un abuelo/a en común.

	public static Rela apartadoC(String nombrePersona1, String nombrePersona2, Graph<Personas, Relacion> g) {
		Personas persona1 = g.vertexSet().stream().filter(v -> v.nombre().equals(nombrePersona1)).findFirst().orElse(null);
		Personas persona2 = g.vertexSet().stream().filter(v -> v.nombre().equals(nombrePersona2)).findFirst().orElse(null);

		if(!Collections.disjoint(Graphs.predecessorListOf(g, persona1), Graphs.predecessorListOf(g, persona2))) return Rela.Hermanos;
		else {
			List<Personas> abuelos1 = new ArrayList<>();
			Graphs.predecessorListOf(g, persona1).forEach(x -> abuelos1.addAll(Graphs.predecessorListOf(g, x)));
			List<Personas> abuelos2 = new ArrayList<>();
			Graphs.predecessorListOf(g, persona2).forEach(x -> abuelos2.addAll(Graphs.predecessorListOf(g, x)));
			if(!Collections.disjoint(abuelos1, abuelos2)) return Rela.Primos;
			else return Rela.Otros;
		}				
	}

	//		Implemente un algoritmo que devuelva un conjunto con todas las personas que
	//		tienen hijos/as con distintas personas. Muestre el grafo configurando su apariencia
	//		de forma que se resalten las personas de dicho conjunto.

	public static void apartadoD(String file, Graph<Personas, Relacion> g, String nombreVista){
		Predicate<Personas> pv1 = v -> {
			List<Personas> lista = new ArrayList<>();
			for(Personas y : Graphs.successorListOf(g, v)) {
				for(Personas x : Graphs.predecessorListOf(g, y)) {
					lista.add(x);
				}
			}
			return lista.stream().distinct().count()>2;
		};

		System.out.println(g.vertexSet().stream().filter(x -> pv1.test(x)).collect(Collectors.toSet()));


		String fileRes = "resultados/ejercicio1/" + file + nombreVista + ".gv";
		GraphColors.toDot(g, fileRes,
				v->v.nombre(),e-> e.hijo().toString(),
				v-> GraphColors.colorIf(Color.blue, pv1.test(v)),
				e-> GraphColors.color(Color.black)
				);
		System.out.println("Se ha generado " + fileRes);

	}

	//		Se desea seleccionar el conjunto mínimo de personas para que se cubran todas
	//		las relaciones existentes. Implemente un método que devuelva dicho conjunto.
	//		Muestre el grafo configurando su apariencia de forma que se resalten las personas
	//		de dicho conjunto.

	public static void apartadoE(Graph<Personas, Relacion> g, String file, String nombreVista){
		Graph<Personas, Relacion> g2 = Graphs.undirectedGraph(g);

		GreedyVCImpl<Personas, Relacion> algA = new GreedyVCImpl<>(g2);
		VertexCover<Personas> conjunto = algA.getVertexCover();
		System.out.println("El conjunto esta compuesto por " + conjunto.size() + " personas: ");
		conjunto.forEach(c -> System.out.println(c));

		String fileRes = "resultados/ejercicio1/" + file + nombreVista + ".gv";
		GraphColors.toDot(g, fileRes,
				v->v.nombre(),e-> e.hijo().toString(),
				v-> GraphColors.colorIf(Color.blue, conjunto.contains(v)),
				e-> GraphColors.color(Color.black)
				);
		System.out.println("Se ha generado " + fileRes);

	}


}
