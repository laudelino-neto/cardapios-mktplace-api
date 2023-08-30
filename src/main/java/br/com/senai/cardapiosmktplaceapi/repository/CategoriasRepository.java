package br.com.senai.cardapiosmktplaceapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.senai.cardapiosmktplaceapi.entity.Categoria;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import br.com.senai.cardapiosmktplaceapi.entity.enums.TipoDeCategoria;

@Repository
public interface CategoriasRepository extends JpaRepository<Categoria, Integer>{

	@Query(value = 
			"SELECT c "
			+ "FROM Categoria c "
			+ "WHERE Upper(c.nome) LIKE Upper(:nome) "
			+ "AND c.status = :status "
			+ "AND c.tipo = :tipo "
			+ "ORDER BY c.nome ")
	public Page<Categoria> listarPor(String nome, Status status, 
			TipoDeCategoria tipo, Pageable paginacao);
	
	@Query(value = 
			"SELECT c "
			+ "FROM Categoria c "
			+ "WHERE Upper(c.nome) = :nome ")
	public Categoria buscarPor(String nome);
	
	@Query(value = 
			"SELECT c "
			+ "FROM Categoria c "
			+ "WHERE c.id = :id ")
	public Categoria buscarPor(Integer id);
	
	@Modifying
	@Query(value = "UPDATE Categoria c SET c.status = :status WHERE c.id = :id")
	public void atualizarPor(Integer id, Status status);
	
}
