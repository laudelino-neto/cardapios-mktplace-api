package br.com.senai.cardapiosmktplaceapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import br.com.senai.cardapiosmktplaceapi.entity.Categoria;
import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
public interface RestauranteService {
		
	public Restaurante salvar(
			@NotNull(message = "O restaurante não pode ser nulo")
			Restaurante restaurante);
	
	public void atualizarStatusPor(
			@Positive(message = "O id para atualização deve ser positivo")
			@NotNull(message = "O id é obrigatório")
			Integer id,
			@NotNull(message = "O novo status não pode ser nulo")
			Status status);
	
	public Page<Restaurante> listarPor(
			String nome,
			@NotNull(message = "A categoria para listagem não pode ser nula")
			Categoria categoria,
			Pageable paginacao);
	
	public Restaurante excluirPor(
			@Positive(message = "O id para exclusão deve ser positivo")
			@NotNull(message = "O id é obrigatório")
			Integer id);
	
	public Restaurante buscarPor(
			@Positive(message = "O id para busca deve ser positivo")
			@NotNull(message = "O id é obrigatório")
			Integer id);
	
}
