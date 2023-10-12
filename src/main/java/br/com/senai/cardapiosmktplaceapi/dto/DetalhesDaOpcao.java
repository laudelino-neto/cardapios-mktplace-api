package br.com.senai.cardapiosmktplaceapi.dto;

import java.math.BigDecimal;

import br.com.senai.cardapiosmktplaceapi.entity.enums.Confirmacao;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class DetalhesDaOpcao {
	
	private Status status;
	
	@DecimalMin(message = "O preço não pode ser inferior a R$0.01", value = "0.0", inclusive = false)
    @Digits(message = "O preço deve possuir o formato 'NNNNNNNNN.NN'", integer = 9, fraction = 2)
	private BigDecimal preco;
	
	@NotNull(message = "O indicador de recomendação da opção não deve ser nulo")
	@Column(name = "recomendado")	
	private Confirmacao recomendado;

	@Positive(message = "O id da seção deve ser positivo")
	private Integer idDaSecao;

}
