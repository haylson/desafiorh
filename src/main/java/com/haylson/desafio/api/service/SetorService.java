package com.haylson.desafio.api.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haylson.desafio.api.model.Setor;
import com.haylson.desafio.api.repository.SetorRepository;

@Service
public class SetorService {

	@Autowired
	private SetorRepository setorRepository;

	@Transactional
	public Setor atualizar(Long id, Setor setor) {
		Setor setorSalvo = setorRepository.findById(id).orElse(null);

		if (setorSalvo == null) {
			throw new EmptyResultDataAccessException(1);
		}

		BeanUtils.copyProperties(setor, setorSalvo, "id");
		return setorRepository.save(setorSalvo);
	}

	public void verificarSetorExiste(Setor setor) {
		
		Setor setorPesquisa = setorRepository.findById(setor.getId()).orElse(null);
		
		if (setorPesquisa == null) {
			throw new EmptyResultDataAccessException(1);
		}
	}
	
	@Transactional
	public void salvarListaSetor(List<Setor> listaSetor) {
		setorRepository.saveAll(listaSetor);
	}
	

}
