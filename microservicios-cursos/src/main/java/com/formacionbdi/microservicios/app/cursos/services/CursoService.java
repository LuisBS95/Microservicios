package com.formacionbdi.microservicios.app.cursos.services;

import java.util.List;



import com.formacionbdi.microservicios.app.cursos.entity.Curso;
import com.formacionbdi.microservicios.commons.alumnos.entity.Alumno;
import com.formacionbdi.microservicios.commons.services.CommonService;

public interface CursoService extends CommonService<Curso> {

	public Curso findCursoByAlumnoId(Long id);
	
	public Iterable<Long> obtenerExamenesIdsConRespuestasAlumno( Long id);
	
	public Iterable<Alumno> obtenerAlumnosPorCurso( List<Long> ids);
	
	public void eliminarCursoAlumnoPorId(Long id);
	
}
