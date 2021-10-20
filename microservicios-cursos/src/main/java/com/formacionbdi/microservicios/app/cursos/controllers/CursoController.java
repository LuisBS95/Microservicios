package com.formacionbdi.microservicios.app.cursos.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.formacionbdi.microservicios.app.cursos.entity.Curso;
import com.formacionbdi.microservicios.app.cursos.entity.CursoAlumno;
import com.formacionbdi.microservicios.app.cursos.services.CursoService;
import com.formacionbdi.microservicios.commons.alumnos.entity.Alumno;
import com.formacionbdi.microservicios.commons.controllers.CommonController;
import com.formacionbdi.microservicios.commons.examenes.entity.Examen;

@RestController
public class CursoController extends CommonController<Curso, CursoService> {

	@Value("${config.balanceador.test}")
	private String balanceadorTest;
	
	@DeleteMapping("/eliminar-alumno/{id}")
	public ResponseEntity<?> eliminarCursoAlumnoPorId(@PathVariable Long id){
		service.eliminarCursoAlumnoPorId(id);
		return ResponseEntity.noContent().build();
	}
	
	@Override
	@GetMapping
	public ResponseEntity<?> listar(){
		List<Curso> cursos = (List<Curso>) service.findAll();
		cursos.stream().map(c -> {
			c.getCursoAlumnos().forEach(ca->{
				Alumno alumno = new Alumno();
				alumno.setId(ca.getAlumnoId());
				c.addAlumno(alumno);
			});
			return c;
		}).collect(Collectors.toList());
		return ResponseEntity.ok().body(cursos);
	}
	
	
	@Override
	@GetMapping("/pagina")
	public ResponseEntity<?> listar(Pageable pageable){
		
		Page<Curso> cursos = service.findAll(pageable).map(curso -> {
			curso.getCursoAlumnos().forEach(ca->{
				Alumno alumno = new Alumno();
				alumno.setId(ca.getAlumnoId());
				curso.addAlumno(alumno);
			});
			return curso;
		});
		return ResponseEntity.ok().body(cursos);
	}
	
	@Override
	@GetMapping("/{id}")
	public ResponseEntity<?> ver(@PathVariable Long id){
		Optional<Curso> o = service.findById(id);
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Curso curso = o.get();
		
		
		//Aqui sacamos los ids de los alumnos registrados.
		if(curso.getCursoAlumnos().isEmpty() == false) {
			List<Long> ids = curso.getCursoAlumnos().stream().map(ca ->{
				return ca.getAlumnoId();
			}).collect(Collectors.toList());
			
			List<Alumno> alumnos = (List<Alumno>) service.obtenerAlumnosPorCurso(ids);
			
			curso.setAlumnos(alumnos);
		}
		
		
		
		return ResponseEntity.ok().body(curso);
	}
	
	
	@GetMapping("/balanceador-test")
	public ResponseEntity<?> balanceadorTest() {
		Map<String, Object> response = new HashMap<>();
		response.put("balanceador", balanceadorTest);
		response.put("cursos", service.findAll());
		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> editar(@Valid @PathVariable Long id,@RequestBody Curso curso, BindingResult result){
		
		if(result.hasErrors()) {
			return this.validar(result);
		}
		
		Optional<Curso> o = service.findById(id);
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Curso cursoDb = o.get();
		cursoDb.setNombre(curso.getNombre());
		
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoDb));
	}
	
//	@PutMapping("/{id}/asignar-alumnos")
//	public ResponseEntity<?> asignarAlumnos( @RequestBody List<Alumno> alumnos,@PathVariable Long id){
//		Optional<Curso> o = service.findById(id);
//		if(o.isEmpty()) {
//			return ResponseEntity.notFound().build();
//		}
//		
//		Curso cursoDb = o.get();
//		
//		alumnos.forEach((a)->cursoDb.addAlumno(a));
//		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoDb));
//	}
	
	
	@PutMapping("/{id}/asignar-alumnos")
	public ResponseEntity<?> asignarAlumnos( @RequestBody List<Alumno> alumnos,@PathVariable Long id){
		Optional<Curso> o = service.findById(id);
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Curso cursoDb = o.get();
		
		alumnos.forEach((a)->{
			CursoAlumno cursoAlumno = new CursoAlumno();
			cursoAlumno.setAlumnoId(a.getId());
			cursoAlumno.setCurso(cursoDb);
			cursoDb.addCursoAlumno(cursoAlumno);
		});
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoDb));
	}
	

	@PutMapping("/{id}/eliminar-alumno")
	public ResponseEntity<?> eliminarAlumnos( @RequestBody Alumno alumno,@PathVariable Long id){
		Optional<Curso> o = service.findById(id);
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Curso cursoDb = o.get();
		CursoAlumno cursoAlumno = new CursoAlumno();
		cursoAlumno.setAlumnoId(alumno.getId());
		cursoDb.removeCursoAlumno(cursoAlumno);
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoDb));
	}
	
//	
//	@PutMapping("/{id}/eliminar-alumno")
//	public ResponseEntity<?> eliminarAlumnos( @RequestBody Alumno alumno,@PathVariable Long id){
//		Optional<Curso> o = service.findById(id);
//		if(o.isEmpty()) {
//			return ResponseEntity.notFound().build();
//		}
//		
//		Curso cursoDb = o.get();
//		
//		cursoDb.removeAlumno(alumno);
//		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoDb));
//	}
//	
	@GetMapping("/alumno/{id}")
	public ResponseEntity<?> buscarPorAlumnoId(@PathVariable Long id){
		Curso curso = service.findCursoByAlumnoId(id);
		
		if(curso != null) {
			List<Long> examenesIds = (List<Long>) service.obtenerExamenesIdsConRespuestasAlumno(id);
			
			List<Examen> examenes = curso.getExamenes().stream().map(examen ->{
				if(examenesIds.contains(examen.getId())) {
					examen.setRespondido(true);
				}
				return examen;
			}).collect(Collectors.toList());
			
			curso.setExamenes(examenes);
		}
		return ResponseEntity.ok(curso);
	}
	
	
	//Parte de examenes
	@PutMapping("/{id}/asignar-examenes")
	public ResponseEntity<?> asignarExamen( @RequestBody List<Examen> examenes,@PathVariable Long id){
		Optional<Curso> o = service.findById(id);
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Curso cursoDb = o.get();
		
		examenes.forEach((e)->cursoDb.addExamen(e));
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoDb));
	}
	
	@PutMapping("/{id}/eliminar-examen")
	public ResponseEntity<?> eliminarExamen( @RequestBody Examen examen,@PathVariable Long id){
		Optional<Curso> o = service.findById(id);
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Curso cursoDb = o.get();
		
		cursoDb.removeExamen(examen);
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoDb));
	}
	
	
	
	
}