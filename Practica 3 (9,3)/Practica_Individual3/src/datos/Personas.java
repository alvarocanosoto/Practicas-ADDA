package datos;

import java.util.Objects;

public record Personas(Integer id, String nombre, Integer anyo, String lugarNacimiento) {
	
	
	public static Personas ofFormat(String[] formato) {
		String city = formato[3];
		Integer year = Integer.parseInt(formato[2]);
		String name = formato[1];		
		Integer id = Integer.parseInt(formato[0]);
		return new Personas(id, name, year, city);
	}
	
	
	@Override
	public String toString() {
		return "Personas [id=" + id + ", nombre=" + nombre + ", anyo=" + anyo + ", lugarNacimiento=" + lugarNacimiento
				+ "]";
	}


	@Override
	public int hashCode() {
		return Objects.hash(anyo, id, lugarNacimiento, nombre);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Personas other = (Personas) obj;
		return Objects.equals(anyo, other.anyo) && Objects.equals(id, other.id)
				&& Objects.equals(lugarNacimiento, other.lugarNacimiento) && Objects.equals(nombre, other.nombre);
	}


	public Object getId() {
		return id;
	}

}
