package br.com.senai.cardapiosmktplaceapi.service;

import org.springframework.validation.annotation.Validated;

import br.com.senai.cardapiosmktplaceapi.entity.Opcao;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
public interface OpcaoService {

	public Opcao buscarPor(
			@NotNull(message = "O id da opção é obrigatório")
			@Positive(message = "O id da opção deve ser positivo")
			Integer id);			
	
}
