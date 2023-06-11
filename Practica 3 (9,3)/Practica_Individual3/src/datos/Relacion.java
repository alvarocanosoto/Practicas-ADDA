package datos;


public record Relacion(Integer padre, Integer hijo) {
	
	public static Relacion ofFormat(String[] formato) {
		Integer padre = Integer.parseInt(formato[0]);
		Integer hijo = Integer.parseInt(formato[1]);
		return new Relacion(padre, hijo);
	}
	
	public static Relacion of(Integer padre, Integer hijo) {
		return new Relacion(padre,hijo);
	}

}
