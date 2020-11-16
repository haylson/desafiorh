package com.haylson.desafio.api.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haylson.desafio.api.model.Pessoa;
import com.haylson.desafio.api.repository.PessoaRepository;

@Service
public class PessoaService {
	
	@Autowired
	private PessoaRepository pessoaRepository;

	@Transactional
	public Pessoa atualizar(Long id, Pessoa pessoa) {
		Pessoa pessoaSalva = pessoaRepository.findById(id).orElse(null);
				
		if (pessoaSalva == null) {
			throw new EmptyResultDataAccessException(1);
		} 
			
		BeanUtils.copyProperties(pessoa, pessoaSalva, "id");
		return pessoaRepository.save(pessoaSalva);
	}
	
	@Transactional
	public void salvarListaPessoa(List<Pessoa> listaPessoa) {
		pessoaRepository.saveAll(listaPessoa);
	}

}
