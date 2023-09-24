package br.com.senai.cardapiosmktplaceapi.service;

import org.springframework.validation.annotation.Validated;

import br.com.senai.cardapiosmktplaceapi.entity.Secao;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
public interface SecaoService {

	public Secao buscarPor(
			@NotNull(message = "O id da seção é obrigatório")
			@Positive(message = "O id da seção deve ser positivo")
			Integer id);
	
}
