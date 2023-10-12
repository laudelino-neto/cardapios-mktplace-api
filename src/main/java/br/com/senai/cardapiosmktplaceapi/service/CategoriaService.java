package br.com.senai.cardapiosmktplaceapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import br.com.senai.cardapiosmktplaceapi.entity.Categoria;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import br.com.senai.cardapiosmktplaceapi.entity.enums.TipoDeCategoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Validated
public interface CategoriaService {

	public Categoria salvar(
			@NotNull(message = "A categoria não pode ser nula")
			Categoria categoria);
	
	public void atualizarStatusPor(
			@Positive(message = "O id para atualização deve ser positivo")
			@NotNull(message = "O id é obrigatório")
			Integer id,
			@NotNull(message = "O novo status não pode ser nulo")
			Status status);
	
	public Page<Categoria> listarPor(
			@Size(min = 3, message = "O nome para listagem deve conter 3 ou mais caracteres")
			@NotBlank(message = "O nome para listagem não pode ser nulo")
			String nome,
			@NotNull(message = "O status para listagem não pode ser nulo")
			Status status, 
			@NotNull(message = "O tipo para listagem não pode ser nulo")
			TipoDeCategoria tipo,
			Pageable paginacao);
	
	public Categoria buscarPor(
			@Positive(message = "O id para busca deve ser positivo")
			@NotNull(message = "O id é obrigatório")
			Integer id);
	
	public Categoria excluirPor(
			@Positive(message = "O id para exclusão deve ser positivo")
			@NotNull(message = "O id é obrigatório")
			Integer id);
	
}
