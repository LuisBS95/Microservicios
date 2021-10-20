package com.formacionbdi.microservicios.app.respuestas.entity;

public class Empleado {

	protected String nombre;
	protected String puesto;
	protected Integer nivel;
	
	
	public String obtenerDetalles() {
		return "Nombre: "+nombre+ ", Puesto: "+puesto+",Nivel: "+nivel;
	}
}
