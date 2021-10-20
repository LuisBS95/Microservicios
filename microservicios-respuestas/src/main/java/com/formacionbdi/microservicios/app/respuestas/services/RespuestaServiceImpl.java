package com.formacionbdi.microservicios.app.respuestas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formacionbdi.microservicios.app.respuestas.entity.Respuesta;
import com.formacionbdi.microservicios.app.respuestas.repository.RespuestaRepository;

@Service
public class RespuestaServiceImpl implements RespuestaService {
	
	@Autowired
	private RespuestaRepository repository;

	@Override
	@Transactional
	public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas) {
		
		return repository.saveAll(respuestas);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Respuesta> findRespuestabyAlumnobyExamen(Long alumnoId, Long examenId) {
		return repository.findRespuestabyAlumnobyExamen(alumnoId, examenId);
	}

	@Override
	public Iterable<Long> findExamenesIdsConRespuestasByAlumno(Long alumnoId) {
		
		return repository.findExamenesIdsConRespuestasByAlumno(alumnoId);
	}

}
