package br.com.senai.cardapiosmktplaceapi.dto;

import java.math.BigDecimal;

import br.com.senai.cardapiosmktplaceapi.entity.Secao;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Confirmacao;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NovaOpcaoDoCardapio {
	
	@NotNull(message = "O id da opção do cardápio é obrigatório")
	@Positive(message = "O id da opção deve ser positivo")
	private Integer idDaOpcao;
	
	@DecimalMin(message = "O preço não pode ser inferior a R$0.01", value = "0.0", inclusive = false)
    @Digits(message = "O preço deve possuir o formato 'NNNNNNNNN.NN'", integer = 9, fraction = 2)	
	private BigDecimal preco;
	
	@Enumerated(value = EnumType.STRING)
	@NotNull(message = "O indicador de recomendação da opção não deve ser nulo")
	private Confirmacao recomendado;
	
	@NotNull(message = "A seção é obrigatória")
	private Secao secao;
	
}
