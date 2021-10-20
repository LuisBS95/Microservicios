package com.formacionbdi.microservicios.app.cursos.services;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formacionbdi.microservicios.app.cursos.clients.AlumnoFeignClient;
import com.formacionbdi.microservicios.app.cursos.clients.RespuestasFeignClient;
import com.formacionbdi.microservicios.app.cursos.entity.Curso;
import com.formacionbdi.microservicios.app.cursos.repository.CursoRepository;
import com.formacionbdi.microservicios.commons.alumnos.entity.Alumno;
import com.formacionbdi.microservicios.commons.services.CommonServiceImpl;

@Service
public class CursoServiceImpl extends CommonServiceImpl<Curso, CursoRepository> implements CursoService {

	
	@Autowired
	private RespuestasFeignClient client;
	
	@Autowired
	private AlumnoFeignClient clientAlumno;
	
	@Override
	@Transactional(readOnly=true)
	public Curso findCursoByAlumnoId(Long id) {
		
		return repository.findCursoByAlumnoId(id);
	}

	@Override
	public Iterable<Long> obtenerExamenesIdsConRespuestasAlumno(Long id) {
	
		return client.obtenerExamenesIdsConRespuestasAlumno(id);
	}

	@Override
	public Iterable<Alumno> obtenerAlumnosPorCurso(List<Long> ids) {
		return clientAlumno.obtenerAlumnosPorCurso(ids);
	}

	@Override
	@Transactional
	public void eliminarCursoAlumnoPorId(Long id) {
		repository.eliminarCursoAlumnoPorId(id);
		
	}

	

}
