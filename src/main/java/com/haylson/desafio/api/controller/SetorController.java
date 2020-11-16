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

import com.haylson.desafio.api.model.Setor;
import com.haylson.desafio.api.repository.SetorRepository;
import com.haylson.desafio.api.service.SetorService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.models.Response;

@RestController
@RequestMapping("/setor")
public class SetorController {
	
	@Autowired
	private SetorRepository setorRepository;
	
	@Autowired
	private SetorService setorService;
	
	/*
	 * @Autowired private ApplicationEventPublisher publisher;
	 */
	
	@ApiOperation(value="Recurso para listar todos os setores cadastrados.")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Retornado com sucesso."),
        @ApiResponse(code = 404, message = "Não encontrado.")
	})
	@GetMapping
	public ResponseEntity<List<Setor>> listar() {
		List<Setor> listaSetor = setorRepository.findAll();
		return listaSetor.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(listaSetor);
	}
	
	@ApiOperation(value="Recurso para cadastrar um setor.", response = Response.class)
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cadastrado com sucesso."),
            @ApiResponse(code = 500, message = "Erro no processamento.")
    })
	@PostMapping
	public ResponseEntity<Setor> salvar(@Valid @RequestBody Setor setor, HttpServletResponse response) {
		Setor setorSalvo = setorRepository.save(setor);
		//publisher.publishEvent(new RecursoCriadoEvent(this, response, setorSalvo.getId()));
		return setorSalvo != null ? ResponseEntity.status(HttpStatus.CREATED).body(setorSalvo)
				: ResponseEntity.status(500).build();
	}
	
	@ApiOperation(value="Recurso para pesquisar Setor por Id.", response = Response.class)
    @ApiResponses({
    	@ApiResponse(code = 200, message = "Retornado com sucesso."),
        @ApiResponse(code = 404, message = "Não encontrado.")
    })
	@GetMapping("/{id}")
	public ResponseEntity<Setor> buscarPorId(@PathVariable Long id) {
		Setor setor = setorRepository.findById(id).orElse(null);
		return setor != null ? ResponseEntity.ok(setor) : ResponseEntity.notFound().build();
	}
	
	@ApiOperation(value="Recurso para Excluir Setor.", response = Response.class)
    @ApiResponses({
    	@ApiResponse(code = 204, message = "Excluido com sucesso!"),
        @ApiResponse(code = 404, message = "Não encontrado!")
    })
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		setorRepository.deleteById(id);
	}
	
	@ApiOperation(value="Recurso para Atualizar Setor.", response = Response.class)
    @ApiResponses({
    	@ApiResponse(code = 200, message = "Atualizado com sucesso!"),
        @ApiResponse(code = 404, message = "Não encontrado!")
    })
	@PutMapping("/{id}")
	public ResponseEntity<Setor> atualizar(@PathVariable Long id, @Valid @RequestBody Setor setor) {
		Setor setorSalvo = setorService.atualizar(id, setor);
		return setorSalvo != null ? ResponseEntity.ok(setorSalvo) : ResponseEntity.notFound().build();
	}

}
