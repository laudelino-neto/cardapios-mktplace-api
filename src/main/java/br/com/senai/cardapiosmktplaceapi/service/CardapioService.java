package br.com.senai.cardapiosmktplaceapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import br.com.senai.cardapiosmktplaceapi.dto.CardapioSalvo;
import br.com.senai.cardapiosmktplaceapi.dto.NovoCardapio;
import br.com.senai.cardapiosmktplaceapi.entity.Cardapio;
import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
public interface CardapioService {

	public Cardapio inserir(
			@Valid 
			@NotNull(message = "O cardápio não pode ser nulo")
			NovoCardapio novoCardapio);
	
	public Cardapio alterar(
			@Valid
			@NotNull(message = "O cardápio não pode ser nulo")
			CardapioSalvo cardapioSalvo);
	
	public Page<Cardapio> listarPor(
			@NotNull(message = "O restaurante não pode ser nulo")
			Restaurante restaurante,
			Pageable paginacao);
	
	public Cardapio buscarPor(
			@Positive(message = "O id para atualização deve ser positivo")
			@NotNull(message = "O id é obrigatório")
			Integer id);
	
	public void atualizarStatusPor(
			@Positive(message = "O id para atualização deve ser positivo")
			@NotNull(message = "O id é obrigatório")
			Integer id,
			@NotNull(message = "O novo status não pode ser nulo")
			Status status);
	
}
