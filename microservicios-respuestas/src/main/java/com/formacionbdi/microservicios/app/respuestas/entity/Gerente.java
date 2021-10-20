package com.formacionbdi.microservicios.app.respuestas.entity;

public class Gerente extends Empleado {

	private String departamento;
	
	@Override
	public String obtenerDetalles() {
		return super.obtenerDetalles()+", Departamento: "+departamento;
	}
}
