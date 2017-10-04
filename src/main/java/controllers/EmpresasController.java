package controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import dominio.empresas.Cuenta;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class EmpresasController implements WithGlobalEntityManager, TransactionalOps{
	public static ModelAndView listar(Request req, Response res){
		Map<String, List<Cuenta>> model = new HashMap<>();
		//model.put("empresas", empresas);
		return new ModelAndView(model, "empresas/empresas.hbs");
	}
}
