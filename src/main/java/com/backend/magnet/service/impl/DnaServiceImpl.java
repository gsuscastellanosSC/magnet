package com.backend.magnet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.magnet.dao.DnaDAO;
import com.backend.magnet.model.Dna;
import com.backend.magnet.service.DnaService;

@Service
public class DnaServiceImpl implements DnaService {

	@Autowired
	private DnaDAO dnaDAO;

	@Override
	public Dna save(Dna dna) {
		return dnaDAO.save(dna);
	}

	@Override
	public List<Dna> findAll() {
		return (List<Dna>) dnaDAO.findAll();
	}

	@Override
	public List<Dna> findMutans() {
		return dnaDAO.findMutans();
	}

	@Override
	public List<Dna> findHumans() {
		return dnaDAO.findHumans();
	}

}
