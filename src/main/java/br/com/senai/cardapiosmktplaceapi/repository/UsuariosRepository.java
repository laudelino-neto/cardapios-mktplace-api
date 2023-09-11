package br.com.senai.cardapiosmktplaceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.senai.cardapiosmktplaceapi.entity.Usuario;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuario, String>{

	@Query(value = 
			"SELECT u "
			+ "FROM Usuario u "
			+ "JOIN FETCH u.papel p "
			+ "WHERE u.login = :login ")
	public Usuario buscarPor(String login);
	
}
