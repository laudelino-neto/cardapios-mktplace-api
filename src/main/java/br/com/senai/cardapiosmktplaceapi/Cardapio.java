package br.com.senai.cardapiosmktplaceapi;

import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "cardapios")
@Entity(name = "Cardapio")
public class Cardapio {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@EqualsAndHashCode.Include
	private Integer id;
	
	@Size(max = 100, message = "O nome do cardápio não deve conter mais de 250 caracteres")
	@NotBlank(message = "O nome do restaurante é obrigatório")
	@Column(name = "nome")
	private String nome;
	
	@NotBlank(message = "A descrição do cardápio é obrigatória")	
	@Column(name = "descricao")
	private String descricao;
	
	@Enumerated(value = EnumType.STRING)
	@NotNull(message = "O status do cardápio não deve ser nulo")
	@Column(name = "status")
	private Status status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_restaurante")
	@NotNull(message = "O restaurante é obrigatório")
	private Restaurante restaurante;
	
}
