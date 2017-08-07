package dominio.indicadores;

import dominio.Empresa;

public class ExpresionIndicador implements Expresion{
	
	private String nombreIndicador;
	
	public ExpresionIndicador(String nombreIndicador){
		this.nombreIndicador=nombreIndicador;		
	}
	
	public int evaluarEn(Empresa empresa, String anio){
		Indicador indicador = ArchivoIndicadores.getInstance().buscarIndicador(nombreIndicador);
		return indicador.evaluarEn(empresa,anio);
	}
}