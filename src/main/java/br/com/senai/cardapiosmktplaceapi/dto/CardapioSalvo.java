package br.com.senai.cardapiosmktplaceapi.dto;

import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CardapioSalvo {

	@NotNull(message = "O id do cardápio é obrigatório")
	@Positive(message = "O id do cardápio deve ser positivo")
	private Integer id;
	
	@Size(max = 100, message = "O nome do cardápio não deve conter mais de 250 caracteres")
	@NotBlank(message = "O nome do restaurante é obrigatório")
	private String nome;
	
	@NotBlank(message = "A descrição do cardápio é obrigatória")	
	@Column(name = "descricao")
	private String descricao;
	
	@Enumerated(value = EnumType.STRING)
	@NotNull(message = "O status do cardápio não deve ser nulo")
	private Status status;
	
	@NotNull(message = "O restaurante é obrigatório")
	private Restaurante restaurante;
		
}
