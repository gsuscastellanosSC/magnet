package com.backend.magnet.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.backend.magnet.util.Constant;

@Entity
@Table(name = Constant.ENTITY_TABLE_NAME_DNA)
public class Dna implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dna_id;

	String[] dna;
	boolean mutant;

	private static final long serialVersionUID = Constant.ONE_LONG;

	public Integer getDna_id() {
		return dna_id;
	}

	public void setDna_id(Integer dna_id) {
		this.dna_id = dna_id;
	}

	public String[] getDna() {
		return dna;
	}

	public void setDna(String[] dna) {
		this.dna = dna;
	}

	public boolean isMutant() {
		return mutant;
	}

	public void setMutant(boolean mutant) {
		this.mutant = mutant;
	}

}
