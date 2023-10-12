package br.com.senai.cardapiosmktplaceapi.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "notificacoes")
@Entity(name = "Notificacao")
public class Notificacao {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@EqualsAndHashCode.Include
	private Integer id;

	@Size(max = 100, message = "O destinatário não pode possuir mais de 100 caracteres")
	@NotBlank(message = "O destinatário é obrigagatório")
	@Column(name = "destinatario")
	private String destinatario;
	
	@Size(max = 100, message = "O título não pode possuir mais de 100 caracteres")
	@NotBlank(message = "O título é obrigagatório")
	@Column(name = "titulo")
	private String titulo;
	
	@NotBlank(message = "A mensagem é obrigatória")
	@Column(name = "mensagem")
	private String mensagem;
	
}