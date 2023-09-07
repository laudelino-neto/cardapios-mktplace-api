package br.com.senai.cardapiosmktplaceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.senai.cardapiosmktplaceapi.entity.Secao;

@Repository
public interface SecoesRepository extends JpaRepository<Secao, Integer>{

}
