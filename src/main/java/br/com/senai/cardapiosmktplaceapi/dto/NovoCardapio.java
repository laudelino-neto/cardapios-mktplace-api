package br.com.senai.cardapiosmktplaceapi.dto;

import java.util.ArrayList;
import java.util.List;

import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NovoCardapio {
	
	@Size(max = 100, message = "O nome do cardápio não deve conter mais de 250 caracteres")
	@NotBlank(message = "O nome do restaurante é obrigatório")
	private String nome;
	
	@NotBlank(message = "A descrição do cardápio é obrigatória")
	private String descricao;
	
	@NotNull(message = "O restaurante é obrigatório")
	private Restaurante restaurante;
	
	@Enumerated(value = EnumType.STRING)
	@NotNull(message = "O status do cardápio não deve ser nulo")
	private Status status;
	
	@Size(min = 1, message = "O cardápio deve possuir ao menos uma opção")
	private List<NovaOpcaoDoCardapio> opcoes;
	
	public NovoCardapio() {
		this.opcoes = new ArrayList<>();
		this.status = Status.A;
	}
	
}
