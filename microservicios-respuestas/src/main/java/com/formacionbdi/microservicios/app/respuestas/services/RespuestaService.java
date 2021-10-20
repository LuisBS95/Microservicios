package com.formacionbdi.microservicios.app.respuestas.services;

import com.formacionbdi.microservicios.app.respuestas.entity.Respuesta;

public interface RespuestaService {
	public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas);
	
	public Iterable<Respuesta> findRespuestabyAlumnobyExamen(Long alumnoId, Long examenId);
	
	public Iterable<Long> findExamenesIdsConRespuestasByAlumno(Long alumnoId);
}