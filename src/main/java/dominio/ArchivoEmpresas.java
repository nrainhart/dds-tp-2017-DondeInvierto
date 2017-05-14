package dominio;

import java.util.ArrayList;

public abstract class ArchivoEmpresas {
	
	String ruta;
	ArrayList<Empresa> empresas;
	
	public ArchivoEmpresas(String ruta){
		this.ruta = ruta;
		empresas = new ArrayList<Empresa>();
	}
	
	public boolean puedeLeerArchivo(){
		String extension = "";
		int i = ruta.lastIndexOf('.');
		if (i > 0) {
		    extension = ruta.substring(i+1);
		}
		return extension.equals(this.extensionQueLee());
	}
	
	public void abrirArchivo(){
		this.prepararLector();
		empresas.clear();
	}
	
	public ArrayList<Empresa> getEmpresas() {
		return empresas;
	}
	
	protected abstract void prepararLector();

	protected abstract String extensionQueLee();
}
