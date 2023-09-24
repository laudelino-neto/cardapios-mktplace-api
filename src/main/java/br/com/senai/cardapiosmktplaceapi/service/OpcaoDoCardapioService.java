package br.com.senai.cardapiosmktplaceapi.service;

import org.springframework.validation.annotation.Validated;

import br.com.senai.cardapiosmktplaceapi.dto.DetalhesDaOpcao;
import br.com.senai.cardapiosmktplaceapi.entity.OpcaoDoCardapio;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
public interface OpcaoDoCardapioService {

	public OpcaoDoCardapio inserirPor(
			@NotNull(message = "O id do cardápio é obrigatório")
			@Positive(message = "O id do cardápio deve ser positivo")
			Integer idDoCardapio,
			@NotNull(message = "O id da opção é obrigatório")
			@Positive(message = "O id da opção deve ser positivo")
			Integer idDaOpcao, 
			@Valid
			@NotNull(message = "Os detalhes da opção do cardápio são obrigatórios")
			DetalhesDaOpcao detalhes);
	
	public OpcaoDoCardapio alterarPor(
			@NotNull(message = "O id do cardápio é obrigatório")
			@Positive(message = "O id do cardápio deve ser positivo")
			Integer idDoCardapio,
			@NotNull(message = "O id da opção é obrigatório")
			@Positive(message = "O id da opção deve ser positivo")
			Integer idDaOpcao,
			@Valid
			@NotNull(message = "Os detalhes da opção do cardápio são obrigatórios")
			DetalhesDaOpcao detalhes);
	
	public OpcaoDoCardapio buscarPor(
			@NotNull(message = "O id do cardápio é obrigatório")
			@Positive(message = "O id do cardápio deve ser positivo")
			Integer idDoCardapio,
			@NotNull(message = "O id da opção é obrigatório")
			@Positive(message = "O id da opção deve ser positivo")
			Integer idDaOpcao);
	
}
