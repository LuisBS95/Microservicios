package com.formacionbdi.microservicios.app.examenes.repository;


import org.springframework.data.repository.PagingAndSortingRepository;

import com.formacionbdi.microservicios.commons.examenes.entity.Asignatura;

public interface AsignaturaRepository extends PagingAndSortingRepository<Asignatura, Long>  {

}
