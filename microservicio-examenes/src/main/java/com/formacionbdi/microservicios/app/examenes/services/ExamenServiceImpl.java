package com.formacionbdi.microservicios.app.examenes.services;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formacionbdi.microservicios.app.examenes.repository.AsignaturaRepository;
import com.formacionbdi.microservicios.app.examenes.repository.ExamenRepository;
import com.formacionbdi.microservicios.commons.examenes.entity.Asignatura;
import com.formacionbdi.microservicios.commons.examenes.entity.Examen;
import com.formacionbdi.microservicios.commons.services.CommonServiceImpl;



@Service
public class ExamenServiceImpl extends CommonServiceImpl<Examen, ExamenRepository> implements ExamenService {

	@Autowired
	private AsignaturaRepository asignaturaRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Examen> findByNombre(String term) {
		
		return repository.findByNombre(term);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Asignatura> findAllAsignaturas() {
		
		return (List<Asignatura>) asignaturaRepository.findAll();
	}

	

}
