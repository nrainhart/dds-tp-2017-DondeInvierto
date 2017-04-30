package dominio;

import java.util.ArrayList;

public class Empresa {
	private String nombre;
	private ArrayList<Cuenta> cuentas;
	
	public Empresa(String nombre,ArrayList<Cuenta> cuentas){
		this.nombre = nombre;
		this.cuentas = cuentas;
	}
	
	public int cantidadDeCuentasQuePosee(){
		return cuentas.size();
	}
	
	public String getNombre(){
		return nombre;
	}

	public ArrayList<Cuenta> getCuentas() {
		return cuentas;
	}
}
