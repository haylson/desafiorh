package com.haylson.desafio.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.haylson.desafio.api.model.Pessoa;
import com.haylson.desafio.api.model.Setor;
import com.haylson.desafio.api.model.dto.Mockapi;
import com.haylson.desafio.api.repository.SetorRepository;

@Service
public class MockapiService {

	@Autowired
	private PessoaService pessoaService;

	@Autowired
	private SetorRepository setorRepository;

	@Transactional
	public void consumirMockapi() throws Exception {

		RestTemplate template = new RestTemplate();
		final String API = "https://5e61af346f5c7900149bc5b3.mockapi.io/desafio03/employer";

		String response = template.getForObject(API, String.class);
		ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
				false);
		JsonNode jsonNode = null;
		
		List<Pessoa> listaPessoa = new ArrayList<Pessoa>();

		try {
			jsonNode = objectMapper.readTree(response);
			List<Mockapi> mockapiList = objectMapper.readValue(String.valueOf(jsonNode),
					objectMapper.getTypeFactory().constructCollectionType(List.class, Mockapi.class));

			mockapiList.stream().forEach(mockapi -> {
				Setor setor = new Setor();
				setor.setCareer(mockapi.getCareer());
				setor.setDepartment(mockapi.getDepartment());
				setor = setorRepository.save(setor);

				Pessoa pessoa = new Pessoa();
				//pessoa.setId(mockapi.getId());
				pessoa.setFirstName(mockapi.getFirst_name());
				pessoa.setLastName(mockapi.getLast_name());
				pessoa.setSetor(setor);
				
				listaPessoa.add(pessoa);
			});
			
			pessoaService.salvarListaPessoa(listaPessoa);

		} catch (Exception e) {
			throw new Exception("Erro na requisição.");
		}

	}

}
