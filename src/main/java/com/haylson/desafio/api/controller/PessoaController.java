package com.haylson.desafio.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.haylson.desafio.api.model.Pessoa;
import com.haylson.desafio.api.model.Setor;
import com.haylson.desafio.api.repository.PessoaRepository;
import com.haylson.desafio.api.service.PessoaService;
import com.haylson.desafio.api.service.SetorService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.models.Response;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private SetorService setorService;
	
	/*
	 * @Autowired private ApplicationEventPublisher publisher;
	 */
	
	@ApiOperation(value="Recurso para listar todas as pessoas cadastradas.")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Retornado com sucesso!"),
        @ApiResponse(code = 404, message = "Não encontrado!")
	})
	@GetMapping
	public ResponseEntity<List<Pessoa>> listar() {
		List<Pessoa> listaPessoa = pessoaRepository.findAll();
		return listaPessoa.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(listaPessoa);
	}
	
	@ApiOperation(value="Recurso para cadastrar uma pessoa.", response = Response.class)
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cadastrado com sucesso."),
            @ApiResponse(code = 500, message = "Erro no processamento.")
    })
	@PostMapping
	public ResponseEntity<Pessoa> salvar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
		Pessoa pessoaSalva = pessoaRepository.save(pessoa);
		//publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getId()));
		return pessoaSalva != null ? ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva)
				: ResponseEntity.status(500).build();
	}
	
	@ApiOperation(value="Recurso para pesquisar Pessoa por Id.", response = Response.class)
    @ApiResponses({
    	@ApiResponse(code = 200, message = "Retornado com sucesso."),
        @ApiResponse(code = 404, message = "Não encontrado.")
    })
	@GetMapping("/{id}")
	public ResponseEntity<Pessoa> buscarPorId(@PathVariable Long id) {
		Pessoa pessoa = pessoaRepository.findById(id).orElse(null);
		return pessoa != null ? ResponseEntity.ok(pessoa) : ResponseEntity.notFound().build();
	}
	
	@ApiOperation(value="Recurso para Excluir Pessoa.", response = Response.class)
    @ApiResponses({
    	@ApiResponse(code = 204, message = "Excluido com sucesso!"),
        @ApiResponse(code = 404, message = "Não encontrado!")
    })
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		pessoaRepository.deleteById(id);
	}
	
	@ApiOperation(value="Recurso para Atualizar Pessoa.", response = Response.class)
    @ApiResponses({
    	@ApiResponse(code = 200, message = "Atualizado com sucesso!"),
        @ApiResponse(code = 404, message = "Não encontrado!")
    })
	@PutMapping("/{id}")
	public ResponseEntity<Pessoa> atualizar(@PathVariable Long id, @Valid @RequestBody Pessoa pessoa) {
		Pessoa pessoaSalva = pessoaService.atualizar(id, pessoa);
		return pessoaSalva != null ? ResponseEntity.ok(pessoaSalva) : ResponseEntity.notFound().build();
	}
	
	@ApiOperation(value="Recurso para Pesquisar Pessoas por Setor através de Json.", response = Response.class)
    @ApiResponses({
    	@ApiResponse(code = 200, message = "Retornado com sucesso!"),
        @ApiResponse(code = 404, message = "Não encontrado!")
    })
	@PostMapping("/setor/") 
	public ResponseEntity<List<Pessoa>> listarPessoasPorSetor(@RequestBody Setor setor) {
		setorService.verificarSetorExiste(setor);
		return ResponseEntity.ok(pessoaRepository.findBySetor(setor));
	}
	
	@ApiOperation(value="Recurso para Pesquisar Pessoas por Setor pelo id de Setor.", response = Response.class)
    @ApiResponses({
    	@ApiResponse(code = 200, message = "Retornado com sucesso!"),
        @ApiResponse(code = 404, message = "Não encontrado!")
    })
	@GetMapping("/setor/{id}") 
	public ResponseEntity<List<Pessoa>> listarPessoasPorIdSetor(@PathVariable Long id) {
		Setor setor = new Setor();
		setor.setId(id);
		setorService.verificarSetorExiste(setor);
		return ResponseEntity.ok(pessoaRepository.findBySetor(setor));
	}

}
