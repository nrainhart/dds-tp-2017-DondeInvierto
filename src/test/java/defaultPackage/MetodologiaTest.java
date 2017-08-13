package defaultPackage;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import dominio.ArchivoXLS;
import dominio.Empresa;
import dominio.indicadores.ArchivoIndicadores;
import dominio.indicadores.ExpresionCuenta;
import dominio.indicadores.Indicador;
import dominio.metodologias.*;

public class MetodologiaTest {
	
	private ArrayList<Indicador> indicadores;
	private ArrayList<Empresa> empresasParaIndicadores;
	
	@Before
	public void setUp() {
		ArchivoXLS archivoEjemploIndicadores = new ArchivoXLS("src/test/resources/EjemploIndicadores.xls");
		ArchivoIndicadores archivoIndicadores = ArchivoIndicadores.getInstance();
		archivoIndicadores.cambiarPath("src/main/resources/Indicadores.txt");
		archivoEjemploIndicadores.leerEmpresas();
		archivoIndicadores.leerIndicadores();
		indicadores = archivoIndicadores.getIndicadores();
		empresasParaIndicadores = archivoEjemploIndicadores.getEmpresas();
		//Ver formas de testear métodos que usan fecha actual (QUIZAS CONVENGA USAR INYECCION DE DEPENDENCIAS) (!!!)
	}
	
	@Test
	public void miEmpresaEsMasAntiguaQueEmpresaReLoca() {
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Empresa empresaReLoca = empresasParaIndicadores.get(2);
		assertTrue(new CondicionPrioritaria(new OperandoCondicion(new Ultimo(), new Antiguedad(), 1), new Mayor()).esMejorQue(miEmpresa, empresaReLoca));
	}
	
	/*@Test
	public void elIndicadorDosDeMiEmpresaEsMayorQueElDeEmpresaReLoca() { NO ENCUENTRA CUENTAS
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Empresa empresaReLoca = empresasParaIndicadores.get(2);
		Indicador indicadorDos = indicadores.get(1);
		assertTrue(new CondPriIndicador(indicadorDos, new Mayor()).esMejorQue(miEmpresa, empresaReLoca));
	}*/
	
	//FALTA TEST DE CondPriConsistencia
	
	@Test
	public void miEmpresaCumpleCondTaxAntiguedadMenorA10() {
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		assertTrue(new CondicionTaxativa(new OperandoCondicion(new Ultimo(), new Antiguedad(), 1), new Menor(), 10).laCumple(miEmpresa));
	}
	
	@Test
	public void empresaReLocaNoCumpleCondTaxAntiguedadMayorA3() {
		Empresa empresaReLoca = empresasParaIndicadores.get(2);
		assertFalse(new CondicionTaxativa(new OperandoCondicion(new Ultimo(), new Antiguedad(), 1), new Mayor(), 3).laCumple(empresaReLoca));
	}
	
	@Test
	public void empresaReLocaCumpleCondTaxAntiguedadIgualA1(){
		Empresa miEmpresa = empresasParaIndicadores.get(2);
		assertTrue(new CondicionTaxativa(new OperandoCondicion(new Ultimo(), new Antiguedad(), 1), new Igual(), 1).laCumple(miEmpresa));
	}
	
	
	/*@Test
	public void empresaReLocaCumpleCondTaxEBITDAMenorAUnMillon(){
		Empresa miEmpresa = empresasParaIndicadores.get(2);
		assertTrue(new CondicionTaxativa(new OperandoCondicion(new Ultimo(), new ExpresionCuenta("EBITDA"), 1), new Menor(), 1000000).laCumple(miEmpresa));
	}
	
	@Test
	public void empresaReLocaCumpleCondTaxEBITDAMayorAUnMil(){
		Empresa miEmpresa = empresasParaIndicadores.get(2);
		assertTrue(new CondicionTaxativa(new OperandoCondicion(new Ultimo(), new ExpresionCuenta("EBITDA"), 1), new Mayor(), 1000).laCumple(miEmpresa));
	}
	
	@Test
	public void empresaReLocaCumpleCondTaxEBITDAIgualA180000(){
		Empresa miEmpresa = empresasParaIndicadores.get(2);
		assertTrue(new CondicionTaxativa(new OperandoCondicion(new Ultimo(), new ExpresionCuenta("EBITDA"), 1), new Igual(), 180000).laCumple(miEmpresa));
	}
	
	@Test
	public void empresaReLocaCumpleCondTaxEBITDAMenorAUnMillonEnUltimosDosAnios(){
		Empresa miEmpresa = empresasParaIndicadores.get(2);
		assertTrue(new CondicionTaxativa(new OperandoCondicion(new Ultimo(), new ExpresionCuenta("EBITDA"), 2), new Menor(), 1000000).laCumple(miEmpresa));
	}
	
	@Test
	public void empresaReLocaCumpleCondTaxEBITDAMayorAUnMilEnUltimosDosAnios(){
		Empresa miEmpresa = empresasParaIndicadores.get(2);
		assertTrue(new CondicionTaxativa(new OperandoCondicion(new Ultimo(), new ExpresionCuenta("EBITDA"), 2), new Mayor(), 1000).laCumple(miEmpresa));
	}
	
	@Test
	public void empresaReLocaCumpleCondTaxEBITDAIgualA180000EnUltimosDosAnios(){
		Empresa miEmpresa = empresasParaIndicadores.get(2);
		assertTrue(new CondicionTaxativa(new OperandoCondicion(new Ultimo(), new ExpresionCuenta("EBITDA"), 2), new Igual(), 180000).laCumple(miEmpresa));
	}*/
	
	@Test
	public void empresaReLocaNOCumpleCondTaxIndicadorINDICADORDOSMayorA360000() { //VER POR QUE ROMPE !!
		Empresa empresaReLoca = empresasParaIndicadores.get(2);
		Indicador indicadorDos = indicadores.get(1);
		OperandoCondicion operando = new OperandoCondicion(new Ultimo(),indicadorDos,1);
		CondicionTaxativa cond = new CondicionTaxativa(operando, new Mayor(), 360000);
		assertFalse(cond.laCumple(empresaReLoca));
	}
	
	@Test
	public void empresaReLocaNOCumpleCondTaxIndicadorINDICADORDOSMenorA200000(){
		Empresa empresaReLoca = empresasParaIndicadores.get(2);
		Indicador indicadorDos = indicadores.get(1);
		OperandoCondicion operando = new OperandoCondicion(new Ultimo(),indicadorDos,1);
		CondicionTaxativa cond = new CondicionTaxativa(operando, new Menor(), 200000);
		assertFalse(cond.laCumple(empresaReLoca));
	}
	
	@Test
	public void empresaReLocaCumpleCondTaxIndicadorINDICADORDOSIgualA30000(){
		Empresa empresaReLoca = empresasParaIndicadores.get(2);
		Indicador indicadorDos = indicadores.get(1);
		OperandoCondicion operando = new OperandoCondicion(new Ultimo(),indicadorDos,1);
		CondicionTaxativa cond = new CondicionTaxativa(operando, new Igual(), 300000);
		assertTrue(cond.laCumple(empresaReLoca));
	}
	
	@Test
	public void miEmpresaNOCumpleCondTaxIndicadorINGRESONETOMayorA20000EnUltimoAnio() { //VER POR QUE ROMPE !!
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Indicador ingresoNeto = indicadores.get(0);
		CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Ultimo(),ingresoNeto,1), new Mayor(), 20000);
		assertFalse(cond.laCumple(miEmpresa));
	}
	
	@Test
	public void miEmpresaNOCumpleCondTaxIndicadorINGRESONETOMenorA8000EnUltimoAnio(){
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Indicador ingresoNeto = indicadores.get(0);
		CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Ultimo(),ingresoNeto,1), new Menor(), 8000);
		assertFalse(cond.laCumple(miEmpresa));
	}
	
	@Test
	public void miEmpresaCumpleCondTaxIndicadorINGRESONETOIgualA17000EnUltimoAnio(){
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Indicador ingresoNeto = indicadores.get(0);
		CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Ultimo(),ingresoNeto,1), new Igual(), 17000);
		assertTrue(cond.laCumple(miEmpresa));
	}
	
	@Test
	public void miEmpresaNOCumpleCondTaxIndicadorINGRESONETOConSumatoriaMayorA30000EnUltimosDosAnios() { //VER POR QUE ROMPE !!
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Indicador ingresoNeto = indicadores.get(0);
		CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Sumatoria(),ingresoNeto,2), new Mayor(), 30000);
		assertFalse(cond.laCumple(miEmpresa));
	}
	
	@Test
	public void miEmpresaNOCumpleCondTaxIndicadorINGRESONETOConSumatoriaMenorA15000EnUltimosDosAnios(){
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Indicador ingresoNeto = indicadores.get(0);
		CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Sumatoria(),ingresoNeto,2), new Menor(), 15000);
		assertFalse(cond.laCumple(miEmpresa));
	}
	
	@Test
	public void miEmpresaCumpleCondTaxIndicadorINGRESONETOConSumatoriaIgualA28000EnUltimosDosAnios(){
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Indicador ingresoNeto = indicadores.get(0);
		CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Sumatoria(),ingresoNeto,2), new Igual(), 28000);
		assertTrue(cond.laCumple(miEmpresa));
	}
	
	@Test
	public void miEmpresaNOCumpleCondTaxIndicadorINGRESONETOConPromedioMayorA15000EnUltimosDosAnios() { //VER POR QUE ROMPE !!
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Indicador ingresoNeto = indicadores.get(0);
		CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Promedio(),ingresoNeto,2), new Mayor(), 15000);
		assertFalse(cond.laCumple(miEmpresa));
	}
	
	@Test
	public void miEmpresaNOCumpleCondTaxIndicadorINGRESONETOConPromedioMenorA10000EnUltimosDosAnios(){
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Indicador ingresoNeto = indicadores.get(0);
		CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Promedio(),ingresoNeto,2), new Menor(), 10000);
		assertFalse(cond.laCumple(miEmpresa));
	}
	
	@Test
	public void miEmpresaCumpleCondTaxIndicadorINGRESONETOConPromedioIgualA14000EnUltimosDosAnios(){
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Indicador ingresoNeto = indicadores.get(0);
		CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Promedio(),ingresoNeto,2), new Igual(), 14000);
		assertTrue(cond.laCumple(miEmpresa));
	}
	
	@Test
	public void miEmpresaNOCumpleCondTaxIndicadorINGRESONETOConPromedioMayorA10000EnUltimosCuatroAnios() { //VER POR QUE ROMPE !!
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Indicador ingresoNeto = indicadores.get(0);
		CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Promedio(),ingresoNeto,4), new Mayor(), 10000);
		assertFalse(cond.laCumple(miEmpresa));
	}
	
	@Test
	public void miEmpresaNOCumpleCondTaxIndicadorINGRESONETOConPromedioMenorA8000EnUltimosCuatroAnios(){
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Indicador ingresoNeto = indicadores.get(0);
		CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Promedio(),ingresoNeto,4), new Menor(), 8000);
		assertFalse(cond.laCumple(miEmpresa));
	}
	
	@Test
	public void miEmpresaCumpleCondTaxIndicadorINGRESONETOConPromedioIgualA9500EnUltimosCuatroAnios(){
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Indicador ingresoNeto = indicadores.get(0);
		CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Promedio(),ingresoNeto,4), new Igual(), 9500);
		assertTrue(cond.laCumple(miEmpresa));
	}
	
	@Test
	public void miEmpresaNOCumpleCondTaxIndicadorINGRESONETOConMedianaMayorA15000EnUltimosTresAnios() { // Mediana con n impar
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Indicador ingresoNeto = indicadores.get(0);
		CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Mediana(),ingresoNeto,3), new Mayor(), 15000);
		assertFalse(cond.laCumple(miEmpresa));
	}
	
	@Test
	public void miEmpresaNOCumpleCondTaxIndicadorINGRESONETOConMedianaMenorA10000EnUltimosTresAnios(){
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Indicador ingresoNeto = indicadores.get(0);
		CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Mediana(),ingresoNeto,3), new Menor(), 10000);
		assertFalse(cond.laCumple(miEmpresa));
	}
	
	@Test
	public void miEmpresaCumpleCondTaxIndicadorINGRESONETOConMedianaIgualA11000EnUltimosTresAnios(){
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Indicador ingresoNeto = indicadores.get(0);
		CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Mediana(),ingresoNeto,3), new Igual(), 11000);
		assertTrue(cond.laCumple(miEmpresa));
	}
	
	@Test
	public void miEmpresaNOCumpleCondTaxIndicadorINGRESONETOConMedianaMayorA10000EnUltimosCuatroAnios() { // Mediana con n impar
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Indicador ingresoNeto = indicadores.get(0);
		CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Mediana(),ingresoNeto,4), new Mayor(), 10000);
		assertFalse(cond.laCumple(miEmpresa));
	}
	
	@Test
	public void miEmpresaNOCumpleCondTaxIndicadorINGRESONETOConMedianaMenorA7000EnUltimosCuatroAnios(){
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Indicador ingresoNeto = indicadores.get(0);
		CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Mediana(),ingresoNeto,4), new Menor(), 7000);
		assertFalse(cond.laCumple(miEmpresa));
	}
	
	@Test
	public void miEmpresaCumpleCondTaxIndicadorINGRESONETOConMedianaIgualA9000EnUltimosCuatroAnios(){
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Indicador ingresoNeto = indicadores.get(0);
		CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Mediana(),ingresoNeto,4), new Igual(), 9000);
		assertTrue(cond.laCumple(miEmpresa));
	}
	
	@Test
	public void miEmpresaNOCumpleCondTaxIndicadorINGRESONETOConVariacionMayorA15000EnUltimosCuatroAnios() { // Mediana con n impar
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Indicador ingresoNeto = indicadores.get(0);
		CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Variacion(),ingresoNeto,4), new Mayor(), 15000);
		assertFalse(cond.laCumple(miEmpresa));
	}
	
	@Test
	public void miEmpresaNOCumpleCondTaxIndicadorINGRESONETOConVariacionMenorA13000EnUltimosCuatroAnios(){
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Indicador ingresoNeto = indicadores.get(0);
		CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Variacion(),ingresoNeto,4), new Menor(), 13000);
		assertFalse(cond.laCumple(miEmpresa));
	}
	
	@Test
	public void miEmpresaCumpleCondTaxIndicadorINGRESONETOConVariacionIgualA14000EnUltimosCuatroAnios(){
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Indicador ingresoNeto = indicadores.get(0);
		CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Variacion(),ingresoNeto,4), new Igual(), 14000);
		assertTrue(cond.laCumple(miEmpresa));
	}
	
	@Test
	public void empresaReLocaLanzaErrorAlQuererCumplirCondTaxIndicadorINGRESONETOConVariacionIgualA14000EnUltimosTresAniosPorFaltaDeCuentas(){
		Empresa empresaReLoca = empresasParaIndicadores.get(2);
		Indicador ingresoNeto = indicadores.get(0);
		boolean huboError = false;
		try{
			CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Variacion(),ingresoNeto,3), new Igual(), 14000);
			cond.laCumple(empresaReLoca);
		}catch(RuntimeException e){ // Catchear NoExisteCuentaError
			huboError = true;
		}
		assertTrue(huboError);
	}
	
	@Test
	public void miEmpresaLanzaErrorAlQuererCumplirCondTaxIndicadorINGRESONETOConSumatoriaIgualA14000EnUltimosCincoAniosPorFaltaDeCuentas(){
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Indicador ingresoNeto = indicadores.get(0);
		boolean huboError = false;
		try{
			CondicionTaxativa cond = new CondicionTaxativa(new OperandoCondicion(new Variacion(),ingresoNeto,5), new Igual(), 50000);
			cond.laCumple(miEmpresa);
		}catch(RuntimeException e){
			huboError = true;
		}
		assertTrue(huboError);
	}
	
	@Test
	public void miEmpresaEsMejorQueEmpresaReLocaConINGRESONETOEnUltimoAnio(){
		Empresa miEmpresa = empresasParaIndicadores.get(0);
		Empresa empresaReLoca = empresasParaIndicadores.get(2);
		Indicador ingresoNeto = indicadores.get(0);
		CondicionPrioritaria cond = new CondicionPrioritaria(new OperandoCondicion(new Ultimo(),ingresoNeto,1), new Mayor());
		assertTrue(cond.esMejorQue(miEmpresa, empresaReLoca));
	}

}
