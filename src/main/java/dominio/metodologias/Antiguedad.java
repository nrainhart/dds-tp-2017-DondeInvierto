package dominio.metodologias;

import java.time.Year;

import javax.persistence.Entity;

import dominio.empresas.Empresa;
import excepciones.AntiguedadMenorACeroError;

@Entity
public class Antiguedad extends Cuantificador {

	public Antiguedad() {
	} // PROBLEMA: SE VAN A ESTAR CREANDO MÚLTIPLES INSTANCIAS DE ANTIGUEDAD, CUANDO
		// UNA SERÍA SUFICIENTE (!!!)

	@Override
	public int evaluarEn(Empresa empresa, Year anio) {
		int antiguedad = Integer.parseInt(anio.toString()) - empresa.getAnioDeCreacion();
		if (antiguedad < 0) {
			throw new AntiguedadMenorACeroError("La empresa " + empresa.getNombre() + " no existía en el año " + anio);
		}
		return antiguedad;
	}
}