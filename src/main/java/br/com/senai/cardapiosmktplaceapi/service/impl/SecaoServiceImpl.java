package br.com.senai.cardapiosmktplaceapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;

import br.com.senai.cardapiosmktplaceapi.entity.Secao;
import br.com.senai.cardapiosmktplaceapi.repository.SecoesRepository;
import br.com.senai.cardapiosmktplaceapi.service.SecaoService;

@Service
public class SecaoServiceImpl implements SecaoService{

	@Autowired
	private SecoesRepository repository;
	
	@Override
	public Secao buscarPor(Integer id) {
		Secao secaoEncontrada = repository.findById(id).get();		
		Preconditions.checkNotNull(secaoEncontrada, 
				"Não existe seção para o id informado");
		Preconditions.checkArgument(secaoEncontrada.isAtiva(), 
				"A seção está inativa");
		return secaoEncontrada;
	}

}
