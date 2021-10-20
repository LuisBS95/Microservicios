package com.formacionbdi.microservicios.app.examenes.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



import com.formacionbdi.microservicios.app.examenes.services.ExamenService;
import com.formacionbdi.microservicios.commons.controllers.CommonController;
import com.formacionbdi.microservicios.commons.examenes.entity.Examen;
import com.formacionbdi.microservicios.commons.examenes.entity.Pregunta;

@RestController
public class ExamenController extends CommonController<Examen, ExamenService> {
	@PutMapping("/{id}")
	public ResponseEntity<?> editar(@Valid @PathVariable Long id,@RequestBody Examen examen , BindingResult result){
		if(result.hasErrors()) {
			return this.validar(result);
		}
		Optional<Examen> o = service.findById(id);
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Examen examenDb = o.get();
		examenDb.setNombre(examen.getNombre());
		
		List<Pregunta> eliminadas = new ArrayList<>();
		
		//Añadiendo a una nueva lista las preguntas que estan en la bd pero ya no en el nuevo objeto
		examenDb.getPreguntas().forEach(pdb->{
			if(!examen.getPreguntas().contains(pdb)) {
				eliminadas.add(pdb);
			}
		});
		
		
		//eliminando las preguntas de la Bd
		eliminadas.forEach(pe ->{
			examenDb.removePregunta(pe);
		});
		
		
		//Ahora tenemos que agregar las nuevas preguntas que se añadieron
		examenDb.setPreguntas(examen.getPreguntas());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(examenDb));
	}
	
	@GetMapping("/filtrar/{term}")
	public ResponseEntity<?> filtrar(@PathVariable String term){
		return ResponseEntity.ok(service.findByNombre(term));
	}
	
	@GetMapping("/asignaturas")
	public ResponseEntity<?> listarAsignaturas(){
		return ResponseEntity.ok(service.findAllAsignaturas());
	}
}
