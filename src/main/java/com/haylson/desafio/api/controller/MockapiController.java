package com.haylson.desafio.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.haylson.desafio.api.service.MockapiService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/mockapi")
public class MockapiController {
	
	@Autowired
	private MockapiService mockapiService;
	
	@ApiOperation(value="Recurso consumir Api do MockApi.")
	@ApiResponses({
        @ApiResponse(code = 201, message = "Atualizado com sucesso!"),
        @ApiResponse(code = 404, message = "Não encontrado!")
	})
	@GetMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void consumirMockapi() throws Exception {
		mockapiService.consumirMockapi();
	}
	

}
