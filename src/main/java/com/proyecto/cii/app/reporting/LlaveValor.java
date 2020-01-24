package com.proyecto.cii.app.reporting;

import java.io.Serializable;
import java.math.BigInteger;

public class LlaveValor implements Serializable{

	private static final long serialVersionUID = 1L;

	private String llave;

	private BigInteger valor;

	public LlaveValor() {		
	}



	public LlaveValor(String llave, BigInteger valor) {
		super();
		this.llave = llave;
		this.valor = valor;
	}



	public String getLlave() {
		return llave;
	}

	public void setLlave(String llave) {
		this.llave = llave;
	}

	public BigInteger getValor() {
		return valor;
	}

	public void setValor(BigInteger valor) {
		this.valor = valor;
	}
}
