package com.backend.magnet.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.magnet.model.Dna;
import com.backend.magnet.service.DnaService;
import com.backend.magnet.util.Constant;

@RestController
@CrossOrigin(origins = { Constant.ASTERISK })
@RequestMapping(Constant.SLASH_API)
public class DnaController {

	@Autowired
	private DnaService dnaService;

	@PostMapping(Constant.SLASH_MUTANT)
	public ResponseEntity<Dna> save(@RequestBody Dna dna) {

		try {
			if (null == dna) {
				return new ResponseEntity<>(dna, HttpStatus.BAD_REQUEST);
			} else {

				if (Constant.INT_TWO == valida(dna.getDna())) {

					dna.setMutant(true);

					dnaService.save(dna);

					return new ResponseEntity<>(dna, HttpStatus.OK);
				} else if (Constant.INT_ONE == valida(dna.getDna())) {
					dna.setMutant(false);

					dnaService.save(dna);

					return new ResponseEntity<>(dna, HttpStatus.FORBIDDEN);
				} else if (0 == valida(dna.getDna())) {

					return new ResponseEntity<>(dna, HttpStatus.BAD_REQUEST);

				}

			}

		} catch (

		Exception e) {
			return new ResponseEntity<>(dna, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(dna, HttpStatus.BAD_REQUEST);

	}

	@GetMapping(Constant.SLASH_STATS)
	public ResponseEntity<JSONObject> findAll() {

		List<Dna> humans = dnaService.findHumans();
		List<Dna> mutans = dnaService.findMutans();
		float human = humans.size();
		float mutant = mutans.size();

		JSONObject json = new JSONObject();

		return new ResponseEntity("{\r\n" + "   \"count_mutant_dna\":\"" + mutant + "\",\r\n"
				+ "   \"count_human_dna\":\"" + human + "\",\r\n" + "   \"ratio\":\"" + mutant / human + "\"\r\n" + "}",
				HttpStatus.BAD_REQUEST);

	}

	static int valida(String[] dna) throws Exception {

		int horizontalBase = Constant.INT_ZERO, lengthDna = Constant.INT_ZERO, horizontal = Constant.INT_ZERO,
				vertical = Constant.INT_ZERO;
		Character[] horizontalSequences = new Character[Constant.INT_LENGTH_SEQUENCES],
				verticalSequences = new Character[Constant.INT_LENGTH_SEQUENCES],
				oblicuoSequences = new Character[Constant.INT_LENGTH_SEQUENCES];
		Map<Integer, Character[]> isMutant = new HashMap<>();

		lengthDna = dna.length;

		if (lengthDna > Constant.INT_ZERO) {
			if (lengthDna < Constant.INT_LENGTH_SEQUENCES) {
				return Constant.INT_ZERO;
			} else {

				for (int secuencia = Constant.INT_ZERO; secuencia < lengthDna; secuencia++) {

					if (Constant.INT_ZERO == metodoValida(dna, secuencia)) {
						return Constant.INT_ZERO;
					}

					if (lengthDna != dna[secuencia].length()) {
						return Constant.INT_ZERO;
					}

					for (int base = Constant.INT_ZERO; base < lengthDna; base++) {

						// Validación Oblicua hacia abajo
						vertical = secuencia;
						horizontal = base;
						if (vertical + Constant.INT_LENGTH_SEQUENCES_MINUS_INT_ONE < lengthDna
								&& horizontal + Constant.INT_LENGTH_SEQUENCES_MINUS_INT_ONE < lengthDna) {
							for (int i = Constant.INT_ZERO; i < oblicuoSequences.length; i++) {
								oblicuoSequences[i] = dna[vertical].toUpperCase().charAt(horizontal);
								vertical++;
								horizontal++;
							}
							if (validaMutante(oblicuoSequences)) {

								isMutant.put(Constant.INT_TWO, oblicuoSequences);

							}
						}

						// Validación Oblicua hacia arriba
						vertical = secuencia;
						horizontal = base;
						if (vertical + Constant.INT_LENGTH_SEQUENCES_MINUS_INT_ONE < lengthDna
								&& horizontal - Constant.INT_LENGTH_SEQUENCES_MINUS_INT_ONE >= Constant.INT_ZERO) {
							for (int oblicuoBase = Constant.INT_ZERO; oblicuoBase < oblicuoSequences.length; oblicuoBase++) {
								oblicuoSequences[oblicuoBase] = dna[vertical].toUpperCase().charAt(horizontal);
								vertical++;
								horizontal--;
							}
							if (validaMutante(oblicuoSequences)) {
								isMutant.put(Constant.INT_TWO, oblicuoSequences);
							}

						}

						// Validación Vertical Hacia Abajo
						vertical = secuencia;
						horizontal = base;
						if (vertical + Constant.INT_LENGTH_SEQUENCES_MINUS_INT_ONE < lengthDna
								&& horizontal < lengthDna) {
							for (int verticalBase = Constant.INT_ZERO; verticalBase < verticalSequences.length; verticalBase++) {
								verticalSequences[verticalBase] = dna[vertical].toUpperCase().charAt(horizontal);
								vertical++;
							}
							if (validaMutante(verticalSequences)) {
								isMutant.put(Constant.INT_TWO, verticalSequences);
							}
						}

						// Validación Vertical Hacia Arriba
						vertical = secuencia;
						horizontal = base;
						if (vertical - Constant.INT_LENGTH_SEQUENCES_MINUS_INT_ONE > Constant.INT_ZERO
								&& horizontal < lengthDna) {
							for (int verticalBase = Constant.INT_ZERO; verticalBase < verticalSequences.length; verticalBase++) {
								verticalSequences[verticalBase] = dna[vertical].toUpperCase().charAt(horizontal);
								vertical--;
							}

							if (validaMutante(verticalSequences)) {
								isMutant.put(Constant.INT_TWO, verticalSequences);
							}
						}

						// Validación Horizontal
						horizontalSequences[horizontalBase] = dna[secuencia].toUpperCase().charAt(base);
						horizontalBase++;
						if (horizontalBase == horizontalSequences.length) {
							horizontalBase = Constant.INT_ZERO;
						}

						if (null != horizontalSequences[Constant.INT_ZERO]
								&& null != horizontalSequences[Constant.INT_ONE] && null != horizontalSequences[2]
								&& null != horizontalSequences[3]) {

							if (validaMutante(horizontalSequences)) {
								isMutant.put(2, horizontalSequences);

							}
						}
					}

					horizontalBase = Constant.INT_ZERO;
				}
			}

		}

		return isMutant.containsKey(Constant.INT_TWO) ? Constant.INT_TWO : Constant.INT_ONE;

	}

	private static int metodoValida(String[] dna, int secuencia) {
		if (dna[secuencia].toUpperCase().contains("B") || dna[secuencia].toUpperCase().contains("D")
				|| dna[secuencia].toUpperCase().contains("E") || dna[secuencia].toUpperCase().contains("F")
				|| dna[secuencia].toUpperCase().contains("H") || dna[secuencia].toUpperCase().contains("I")
				|| dna[secuencia].toUpperCase().contains("J") || dna[secuencia].toUpperCase().contains("K")
				|| dna[secuencia].toUpperCase().contains("L") || dna[secuencia].toUpperCase().contains("M")
				|| dna[secuencia].toUpperCase().contains("N") || dna[secuencia].toUpperCase().contains("Ñ")
				|| dna[secuencia].toUpperCase().contains("O") || dna[secuencia].toUpperCase().contains("P")
				|| dna[secuencia].toUpperCase().contains("Q") || dna[secuencia].toUpperCase().contains("R")
				|| dna[secuencia].toUpperCase().contains("S") || dna[secuencia].toUpperCase().contains("U")
				|| dna[secuencia].toUpperCase().contains("V") || dna[secuencia].toUpperCase().contains("W")
				|| dna[secuencia].toUpperCase().contains("X") || dna[secuencia].toUpperCase().contains("Y")
				|| dna[secuencia].toUpperCase().contains("Z")) {

			return Constant.INT_ZERO;
		}
		return Constant.INT_TRHEE;
	}

	static boolean validaMutante(Character[] sequences) {

		Boolean isMutant = true;

		for (int base = Constant.INT_ONE; base < sequences.length; base++) {

			if (sequences[base] != sequences[base - Constant.INT_ONE]) {
				isMutant = false;
				break;
			}

		}

		return isMutant;
	}
}
