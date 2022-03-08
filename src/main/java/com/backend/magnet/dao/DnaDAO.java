package com.backend.magnet.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.backend.magnet.model.Dna;

public interface DnaDAO extends CrudRepository<Dna, Integer> {

	@Query("select d from Dna d where d.mutant=true")
	List<Dna> findMutans();

	@Query("select d from Dna d where d.mutant=false")
	List<Dna> findHumans();
}
