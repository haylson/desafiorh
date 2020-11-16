package com.haylson.desafio.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.haylson.desafio.api.model.Pessoa;
import com.haylson.desafio.api.model.Setor;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
	
	public List<Pessoa> findBySetor(Setor setor);

}
