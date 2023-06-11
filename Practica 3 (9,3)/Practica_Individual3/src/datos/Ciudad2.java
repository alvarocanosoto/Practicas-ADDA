package datos;

public record Ciudad2(String id, Integer puntuacion) {
	public static Ciudad2 ofFormat(String[] formato) {
		String nombre = formato[0];
		Integer puntuacion = Integer.parseInt(formato[1].replace("p", "")); //para quitar la p
		return new Ciudad2(nombre,puntuacion);
	}
	
	@Override
	public String toString() {
		return this.id;
	}
}
