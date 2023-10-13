package br.com.senai.cardapiosmktplaceapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.senai.cardapiosmktplaceapi.entity.Opcao;
import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;

@Repository
public interface OpcoesRepository extends JpaRepository<Opcao, Integer>{
	
	@Query(value = 
			"SELECT o "
			+ "FROM Opcao o "
			+ "WHERE Upper(o.nome) = Upper(:nome) "
			+ "AND o.restaurante = :restaurante ")
	public Opcao buscarPor(String nome, Restaurante restaurante);
	
	@Query(value = 
			"SELECT o "
			+ "FROM Opcao o "
			+ "JOIN FETCH o.categoria "
			+ "JOIN FETCH o.restaurante "
			+ "WHERE o.id = :id ")
	public Opcao buscarPor(Integer id);
	
	@Query(value = 
			"SELECT o "
			+ "FROM Opcao o "
			+ "JOIN FETCH o.categoria "
			+ "JOIN FETCH o.restaurante "
			+ "WHERE Upper(o.nome) LIKE Upper(:nome) "
			+ "AND (:idDaCategoria IS NULL OR o.categoria.id = :idDaCategoria) "
			+ "AND (:idDoRestaurante IS NULL OR o.restaurante.id = :idDoRestaurante) "
			+ "ORDER BY o.nome ")
	public Page<Opcao> listarPor(String nome, Integer idDaCategoria, 
			Integer idDoRestaurante, Pageable paginacao);
	
	@Modifying
	@Query(value = "UPDATE Opcao o SET o.status = :status WHERE o.id = :id")
	public void atualizarPor(Integer id, Status status);

}
