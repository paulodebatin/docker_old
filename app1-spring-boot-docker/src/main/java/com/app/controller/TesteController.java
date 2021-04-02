package com.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.Service1;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@RestController
public class TesteController {
	
	@Autowired
	private Service1 service;
	
	@Value("${nome.servico}")
	private String nomeServico;
	
	
    @RequestMapping("/")
    public String home() {
        return "Serviço executado: " + nomeServico;
    }

	
	
	@RequestMapping("/pessoas")
	// Quando colocar em produção, precisa colocar com um provider de cache (Redis por exemplo). Como não informamos nada, eles usa um HashMap
	// Para limpar o cache, sobre o método que grava/altera/exclui devo utilizar a anotation @CacheEvict (value = "keyHome", allEntries = true)
	//@Cacheable(value = "keyHome") 
	public List<Pessoa> pessoas() {
		
		//System.out.println("Não pegou do cache!");
		
		System.out.println(service.recupera());
		
		List<Pessoa> lPessoa = new ArrayList<>();
		
		for (int i = 0; i < 1000 ; i++) {
			Pessoa pessoa = new Pessoa();
			pessoa.setId(Long.valueOf(i));
			pessoa.setNome("Nome " + i);
			
			lPessoa.add(pessoa);

		}
		return lPessoa;
	}



	
	

	 
	// Poderia usar global no application.properties: spring.jackson.default-property-inclusion=NON_NULL
	//https://medium.com/@matheuscont/performance-em-api-rest-com-spring-boot-fea7f6f424d2
	@JsonInclude(Include.NON_NULL)
	class Pessoa {
		private Long id;
		private String nome;
		private String endereco;
		
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getNome() {
			return nome;
		}
		public void setNome(String nome) {
			this.nome = nome;
		}
		public String getEndereco() {
			return endereco;
		}
		public void setEndereco(String endereco) {
			this.endereco = endereco;
		}
		
	}


}
