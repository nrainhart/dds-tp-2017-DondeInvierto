package dominio;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

import excepciones.EntidadExistenteError;

@MappedSuperclass
public abstract class AbstractLocalRepository<T>{
	
	@Id 
	@GeneratedValue
	private Long id;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="usuario_id")
	protected Set<T> elementos = new HashSet<>();
	
	public List<T> obtenerTodos() {
		return elementos.stream().collect(Collectors.toList());
	}
	
	public void agregar(T elemento){
		if (existe(elemento)){
			throw new EntidadExistenteError(mensajeEntidadExistenteError(elemento));
		}
		elementos.add(elemento);
	}
	
	protected boolean existe(T elemento){
		return this.obtenerTodos().contains(elemento);//Contains chequea con equals
	}
	
	protected abstract String mensajeEntidadExistenteError(T elemento);
}
