package br.com.senai.cardapiosmktplaceapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Embeddable
public class Endereco {

	@Size(max = 80, message = "O nome da cidade não deve conter mais de 80 caracteres")
	@NotBlank(message = "O nome da cidade é obrigatório")
	@Column(name = "cidade")
	private String cidade;
	
	@Size(max = 200, message = "O logradouro não deve conter mais de 200 caracteres")
	@NotBlank(message = "O logradouro é obrigatório")
	@Column(name = "logradouro")
	private String logradouro;
	
	@Size(max = 200, message = "O bairro não deve conter mais de 200 caracteres")
	@NotBlank(message = "O bairro é obrigatório")
	@Column(name = "bairro")
	private String bairro;
	
	@Column(name = "complemento")
	private String complemento;
	
	@NotEmpty(message = "O cep é obrigatório")
	@Size(max = 15, message = "O cep não pode possuir mais de 15 digitos")
	private String cep;
	
}
