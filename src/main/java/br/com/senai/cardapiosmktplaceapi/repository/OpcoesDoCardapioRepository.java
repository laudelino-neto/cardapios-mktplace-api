package br.com.senai.cardapiosmktplaceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.senai.cardapiosmktplaceapi.entity.OpcaoDoCardapio;
import br.com.senai.cardapiosmktplaceapi.entity.composite.OpcaoDoCardapioId;

@Repository
public interface OpcoesDoCardapioRepository extends JpaRepository<OpcaoDoCardapio, OpcaoDoCardapioId>{

	@Query(value = 
			"SELECT oc "
			+ "FROM OpcaoDoCardapio oc "
			+ "JOIN FETCH oc.cardapio c "
			+ "JOIN FETCH oc.opcao o "
			+ "JOIN FETCH oc.secao s "
			+ "WHERE oc.cardapio.id = :idDoCardapio "
			+ "AND oc.opcao.id = :idDaOpcao ")
	public OpcaoDoCardapio buscarOpcaoPor(Integer idDoCardapio, Integer idDaOpcao);
	
	@Query(value = 
			"SELECT Count (oc) "
			+ "FROM OpcaoDoCardapio oc "
			+ "WHERE oc.cardapio.id = :idDoCardapio "
			+ "AND oc.opcao.id = :idDaOpcao ")
	public Long contarPor(Integer idDoCardapio, Integer idDaOpcao);
	
}
