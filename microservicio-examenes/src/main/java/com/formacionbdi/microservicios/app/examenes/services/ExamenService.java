package com.formacionbdi.microservicios.app.examenes.services;


import java.util.List;

import com.formacionbdi.microservicios.commons.examenes.entity.Asignatura;
import com.formacionbdi.microservicios.commons.examenes.entity.Examen;
import com.formacionbdi.microservicios.commons.services.CommonService;

public interface ExamenService extends CommonService<Examen> {
	
	public List<Examen> findByNombre(String term);
	
	public List<Asignatura> findAllAsignaturas();

}
