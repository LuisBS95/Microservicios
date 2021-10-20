package com.formacionbdi.microservicios.app.cursos.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="microservicio-respuestas")
public interface RespuestasFeignClient {
	
	@GetMapping("/alumno/{id}/examenes-respondidos")
	public Iterable<Long> obtenerExamenesIdsConRespuestasAlumno(@PathVariable Long id);
	

}
