package br.com.senai.cardapiosmktplaceapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import br.com.senai.cardapiosmktplaceapi.entity.Opcao;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Validated
public interface OpcaoService {
	
	public Opcao salvar(
			@NotNull(message = "A opção não pode ser nula")
			Opcao opcao);
	
	public Opcao buscarPor(
			@NotNull(message = "O id da opção é obrigatório")
			@Positive(message = "O id da opção deve ser positivo")
			Integer id);
	
	public void atualizarStatusPor(
			@Positive(message = "O id para atualização deve ser positivo")
			@NotNull(message = "O id é obrigatório")
			Integer id,
			@NotNull(message = "O novo status não pode ser nulo")
			Status status);
	
	public Page<Opcao> listarPor(
			@Size(min = 3, message = "O nome para listagem deve conter 3 ou mais caracteres")
			@NotBlank(message = "O nome para listagem não pode ser nulo")
			String nome, 
			Integer idDaCategoria, 
			Integer idDoRestaurante,
			Pageable paginacao);
	
	public Opcao excluirPor(
			@Positive(message = "O id para exclusão deve ser positivo")
			@NotNull(message = "O id é obrigatório")
			Integer id);
	
}
