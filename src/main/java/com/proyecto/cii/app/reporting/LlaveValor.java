package com.proyecto.cii.app.reporting;

import java.io.Serializable;
import java.math.BigDecimal;

public class LlaveValor implements Serializable{

	private static final long serialVersionUID = 1L;

	private String llave;

	private BigDecimal valor;

	public LlaveValor() {		
	}



	public LlaveValor(String llave, BigDecimal valor) {
		super();
		this.llave = llave;
		this.valor = valor;
	}



	public BigDecimal getValor() {
		return valor;
	}



	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}



	public String getLlave() {
		return llave;
	}

	public void setLlave(String llave) {
		this.llave = llave;
	}

	
}
