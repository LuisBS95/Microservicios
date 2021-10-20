package com.formacionbdi.microservicios.app.respuestas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import com.formacionbdi.microservicios.app.respuestas.entity.Respuesta;
import com.formacionbdi.microservicios.app.respuestas.services.RespuestaService;

@RestController
public class RespuestaController {
	
	@Autowired
	private RespuestaService service;
	
	@PostMapping
	public ResponseEntity<?> crear (@RequestBody Iterable<Respuesta> respuestas){
		respuestas =((List<Respuesta>) respuestas).stream().map(r->{
			r.setAlumnoId(r.getAlumno().getId());
			return r;
		}).collect(Collectors.toList());
		Iterable<Respuesta> respuestasDb = service.saveAll(respuestas);
		return ResponseEntity.status(HttpStatus.CREATED).body(respuestasDb);
	}
	
	@GetMapping("/alumno/{alumnoId}/examen/{examenId}")
	public ResponseEntity<?> obtenerRespuestaPorAlumnoyExamen(@PathVariable Long alumnoId ,@PathVariable Long examenId){
		Iterable<Respuesta> respuestas = service.findRespuestabyAlumnobyExamen(alumnoId, examenId);
		return ResponseEntity.ok(respuestas);
	}
	
	@GetMapping("/alumno/{id}/examenes-respondidos")
	public ResponseEntity<?> obtenerExamenesIdsConRespuestasAlumno(@PathVariable Long id){
		Iterable<Long> examenesids = service.findExamenesIdsConRespuestasByAlumno(id);
		return ResponseEntity.ok(examenesids);
	}

}
