package views;
import viewmodels.*;
import dominio.*;
import dominio.empresas.Cuenta;
import dominio.empresas.Empresa;

import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.*;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.tables.Column;
import org.uqbar.arena.widgets.tables.Table;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.WindowOwner;


public class ConsultarCuentasView extends Dialog<ConsultarCuentasViewModel>{
	public ConsultarCuentasView(WindowOwner owner) {
		super(owner, ConsultarCuentasViewModel.getInstance());
	}

	@Override
	protected void createFormPanel(Panel mainPanel) {
		this.setTitle("Consultar Cuentas");
		mainPanel.setLayout(new VerticalLayout());
		
		Selector<Empresa> selector = new Selector<Empresa>(mainPanel);
		selector.bindValueToProperty("empresaSeleccionada");
		selector.bindItemsToProperty("empresas");
		
		Table<Cuenta> tabla = new Table<Cuenta>(mainPanel, Cuenta.class);
		tabla.bindItemsToProperty("cuentasEmpresa");
		
		new Column<Cuenta>(tabla)
	    .setTitle("Anio")
	    .setFixedSize(150)
	    .bindContentsToProperty("anio");
		
		new Column<Cuenta>(tabla)
	    .setTitle("TipoCuenta")
	    .setFixedSize(150)
	    .bindContentsToProperty("tipoCuenta");
		
		new Column<Cuenta>(tabla)
	    .setTitle("Valor")
	    .setFixedSize(150)
	    .bindContentsToProperty("valor");
		
	}
	
}

