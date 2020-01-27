package com.proyecto.cii.app.reporting;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class LlaveValor2 implements Serializable{

	

	private static final long serialVersionUID = 1L;

	private String mes;
	
	

	private String llave;

	private BigDecimal valor;
	
	private Date Fecha;

	public LlaveValor2(String mes, String llave, BigDecimal valor, Date fecha) {
		super();
		this.mes = mes;
		this.llave = llave;
		this.valor = valor;
		Fecha = fecha;
	}
	
	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getLlave() {
		return llave;
	}

	public void setLlave(String llave) {
		this.llave = llave;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Date getFecha() {
		return Fecha;
	}

	public void setFecha(Date fecha) {
		Fecha = fecha;
	}
	
	
	
}
