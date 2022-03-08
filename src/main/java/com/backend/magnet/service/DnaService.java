package com.backend.magnet.service;

import java.util.List;
import com.backend.magnet.model.Dna;

public interface DnaService {

	public Dna save(Dna dna);

	public List<Dna> findAll();

	public List<Dna> findMutans();

	public List<Dna> findHumans();

}
