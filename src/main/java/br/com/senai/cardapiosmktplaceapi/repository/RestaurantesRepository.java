package br.com.senai.cardapiosmktplaceapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.senai.cardapiosmktplaceapi.entity.Categoria;
import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;

@Repository
public interface RestaurantesRepository extends JpaRepository<Restaurante, Integer>{

	@Query(value = 
			"SELECT r "
			+ "FROM Restaurante r "
			+ "JOIN FETCH r.categoria "
			+ "WHERE (:nome IS NULL OR Upper(r.nome) LIKE Upper(:nome)) "
			+ "AND (:categoria IS NULL OR r.categoria = :categoria) "
			+ "ORDER BY r.nome ",
			countQuery = 
				"SELECT Count(r) "
				+ "FROM Restaurante r "
				+ "WHERE (:nome IS NULL OR Upper(r.nome) LIKE Upper(:nome)) "
				+ "AND (:categoria IS NULL OR r.categoria = :categoria) ")
	public Page<Restaurante> listarPor(String nome, 
			Categoria categoria, Pageable paginacao);
	
	@Query(value = 
			"SELECT r "
			+ "FROM Restaurante r "
			+ "WHERE Upper(r.nome) = Upper(:nome) ")
	public Restaurante buscarPor(String nome);
	
	@Query(value = 
			"SELECT r "
			+ "FROM Restaurante r "
			+ "JOIN FETCH r.categoria  "
			+ "WHERE r.id = :id ")
	public Restaurante buscarPor(Integer id);
	
	@Modifying
	@Query(value = "UPDATE Restaurante r SET r.status = :status WHERE r.id = :id")
	public void atualizarPor(Integer id, Status status);
	
	@Query(value = 
			"SELECT Count(r) "
			+ "FROM Restaurante r "
			+ "WHERE r.categoria.id = :idDaCategoria ")
	public Long contarPor(Integer idDaCategoria);
	
}
