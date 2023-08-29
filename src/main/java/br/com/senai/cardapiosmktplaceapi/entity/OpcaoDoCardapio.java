package br.com.senai.cardapiosmktplaceapi.entity;

import java.math.BigDecimal;

import br.com.senai.cardapiosmktplaceapi.Cardapio;
import br.com.senai.cardapiosmktplaceapi.entity.composite.OpcaoDoCardapioId;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Confirmacao;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "opcoes_cardapios")
@Entity(name = "OpcaoDoCardapio")
public class OpcaoDoCardapio {

	@EmbeddedId
	@NotNull(message = "O id da opção do cardápio é obrigatório")
	private OpcaoDoCardapioId id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("idDoCardapio")
	@JoinColumn(name = "id_cardapio")
	@NotNull(message = "O cardápio da opção é obrigatório")
	private Cardapio cardapio;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("idDaOpcao")
	@JoinColumn(name = "id_opcao")
	@NotNull(message = "A opção vinculada ao cardápio é obrigatória")
	private Opcao opcao;
	
	@Enumerated(value = EnumType.STRING)
	@NotNull(message = "O status da opção não deve ser nulo")
	@Column(name = "status")
	private Status status;
	
	@Column(name = "preco")
	@DecimalMin(message = "O preço não pode ser inferior a R$0.01", value = "0.0", inclusive = false)
    @Digits(message = "O preço deve possuir o formato 'NNNNNNNNN.NN'", integer = 9, fraction = 2)
	private BigDecimal preco;
	
	@Enumerated(value = EnumType.STRING)
	@NotNull(message = "O indicador de recomendação da opção não deve ser nulo")
	@Column(name = "recomendado")	
	private Confirmacao recomendado;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_secao")
	@NotNull(message = "A seção é obrigatória")
	private Secao secao;
	
}
