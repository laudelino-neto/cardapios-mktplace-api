package br.com.senai.cardapiosmktplaceapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;

import br.com.senai.cardapiosmktplaceapi.entity.Opcao;
import br.com.senai.cardapiosmktplaceapi.repository.OpcoesRepository;
import br.com.senai.cardapiosmktplaceapi.service.OpcaoService;

@Service
public class OpcaoServiceImpl implements OpcaoService{

	@Autowired
	private OpcoesRepository repository;
	
	@Override
	public Opcao buscarPor(Integer id) {
		Opcao opcaoEncontrada = repository.buscarPor(id);
		Preconditions.checkNotNull(opcaoEncontrada, 
				"Não existe opção para o id informado");
		Preconditions.checkArgument(opcaoEncontrada.isAtiva(), 
				"A opção está inativa");
		return opcaoEncontrada;
	}

}
