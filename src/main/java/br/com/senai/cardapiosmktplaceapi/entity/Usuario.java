package br.com.senai.cardapiosmktplaceapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "usuarios")
@Entity(name = "Usuario")
public class Usuario {
	
	@Id
	@Size(max = 50, message = "O login do usuário não deve conter mais de 50 caracteres")
	@NotBlank(message = "O login do usuário é obrigatório")
	@Column(name = "login")
	private String login;
	
	@NotBlank(message = "A senha do usuário é obrigatória")
	@Column(name = "senha")
	private String senha;
	
	@Size(max = 120, message = "O nome do usuário não deve conter mais de 120 caracteres")
	@NotBlank(message = "O login do usuário é obrigatório")
	@Column(name = "nome")
	private String nome;
	
	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_papel")
	@NotNull(message = "O papel é obrigatório")
	private Papel papel;

}
