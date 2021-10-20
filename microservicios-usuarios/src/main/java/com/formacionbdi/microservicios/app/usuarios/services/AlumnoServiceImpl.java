package com.formacionbdi.microservicios.app.usuarios.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formacionbdi.microservicios.app.usuarios.client.CursoFeignClient;
import com.formacionbdi.microservicios.app.usuarios.repository.AlumnoRepository;
import com.formacionbdi.microservicios.commons.alumnos.entity.Alumno;
import com.formacionbdi.microservicios.commons.services.CommonServiceImpl;

@Service
public class AlumnoServiceImpl extends CommonServiceImpl<Alumno, AlumnoRepository> implements AlumnoService {

	@Autowired
	private CursoFeignClient client;
	
	@Override
	@Transactional(readOnly = true)
	public List<Alumno> findByNombreOrApellido(String term) {
		// TODO Auto-generated method stub
		return repository.findByNombreOrApellido(term);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Alumno> findAllById(Iterable<Long> ids) {
		
		return repository.findAllById(ids);
	}

	@Override
	public void eliminarCursoAlumnoPorId(Long id) {
		client.eliminarCursoAlumnoPorId(id);
		
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		super.deleteById(id);
		this.eliminarCursoAlumnoPorId(id);
	}

	

}
