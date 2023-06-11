package datos;


public record Trayecto(String origen, String destino, Double precio, Double duracion) {
	
	public static Trayecto ofFormat(String[] formato) {
		String origen = formato[0];	
		String destino = formato[1];
		Double precio = Double.valueOf(formato[2].replace("euros", "")); //para quedarme solo con el precio
		Double duracion =  Double.valueOf(formato[3].replace("min", "")); //para quedarme solo con el tiempo
		return new Trayecto(origen, destino, precio, duracion);
	}
	


}
