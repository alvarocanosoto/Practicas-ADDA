package _soluciones;

import java.util.ArrayList;
import java.util.List;

import _datos.DatosEjercicio4;
import _datos.DatosEjercicio4.Cliente;

public class SolucionEjercicio4 {
	
	public static SolucionEjercicio4 of_Range(List<Integer> ls) {
		return new SolucionEjercicio4(ls);
	}

	private Double kms;
	private Double benef;
	private List<Cliente> clientes;

	private SolucionEjercicio4() {
		kms = 0.;
		benef = 0.;
		clientes = new ArrayList<>();
		Cliente c0 = DatosEjercicio4.getCliente(0);
		clientes.add(c0);
	}
	private SolucionEjercicio4(List<Integer> ls) {
		kms = 0.;
		benef = 0.;
		clientes = new ArrayList<>();
		Cliente c0 = DatosEjercicio4.getCliente(0);
		clientes.add(c0);
		for (int i = 0; i < ls.size(); i++) {
			Cliente c = DatosEjercicio4.getCliente(ls.get(i));
			clientes.add(c);
			if (i == 0) {
				if (DatosEjercicio4.existeArista(0, ls.get(i))) {
					kms += DatosEjercicio4.getDistancia(0, ls.get(i));
					benef += DatosEjercicio4.getBeneficio(ls.get(i)) - kms;
				}
			} else {
				if (DatosEjercicio4.existeArista(ls.get(i - 1), ls.get(i))) {
					kms += DatosEjercicio4.getDistancia(ls.get(i - 1), ls.get(i));
					benef += DatosEjercicio4.getBeneficio(ls.get(i)) - kms;
				}
			}
		}

	}
	
	public static SolucionEjercicio4 empty() {
		return new SolucionEjercicio4();
	}

	@Override
	public String toString() {
		List<Integer> ids = clientes.stream().map(c -> c.id()).toList();
		return "Camino a seguir:\n" + ids + "\nDistancia: " + kms + "\nBeneficio: " + benef;
	}
}
