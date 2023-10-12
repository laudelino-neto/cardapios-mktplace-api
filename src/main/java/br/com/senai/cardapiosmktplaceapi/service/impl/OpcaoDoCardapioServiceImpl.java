package br.com.senai.cardapiosmktplaceapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;

import br.com.senai.cardapiosmktplaceapi.dto.DetalhesDaOpcao;
import br.com.senai.cardapiosmktplaceapi.entity.Cardapio;
import br.com.senai.cardapiosmktplaceapi.entity.Opcao;
import br.com.senai.cardapiosmktplaceapi.entity.OpcaoDoCardapio;
import br.com.senai.cardapiosmktplaceapi.entity.Secao;
import br.com.senai.cardapiosmktplaceapi.entity.composite.OpcaoDoCardapioId;
import br.com.senai.cardapiosmktplaceapi.repository.OpcoesDoCardapioRepository;
import br.com.senai.cardapiosmktplaceapi.service.CardapioService;
import br.com.senai.cardapiosmktplaceapi.service.OpcaoDoCardapioService;
import br.com.senai.cardapiosmktplaceapi.service.OpcaoService;
import br.com.senai.cardapiosmktplaceapi.service.SecaoService;

@Service
public class OpcaoDoCardapioServiceImpl implements OpcaoDoCardapioService{
	
	@Autowired
	private OpcoesDoCardapioRepository repository;
	
	@Autowired
	@Qualifier("cardapioServiceImpl")
	private CardapioService cardapioService;
	
	@Autowired
	@Qualifier("opcaoServiceImpl")
	private OpcaoService opcaoService;
	
	@Autowired
	@Qualifier("secaoServiceImpl")
	private SecaoService secaoService;

	@Override
	public OpcaoDoCardapio inserirPor(Integer idDoCardapio, 
			Integer idDaOpcao, DetalhesDaOpcao detalhes) {
		Cardapio cardapio = cardapioService.buscarPor(idDoCardapio);
		Opcao opcao = opcaoService.buscarPor(idDaOpcao);		
		Secao secao = secaoService.buscarPor(detalhes.getIdDaSecao());
		Preconditions.checkArgument(opcao.getRestaurante().equals(
				cardapio.getRestaurante()), "A opção não pertence ao restaurante");
		Long qtdeDeOcorrencias = repository.contarPor(idDoCardapio, idDaOpcao);
		Preconditions.checkArgument(qtdeDeOcorrencias == 0, 
				"A opção '" + idDaOpcao + "' já pertence ao cardápio");
		OpcaoDoCardapio novaOpcao = new OpcaoDoCardapio();
		OpcaoDoCardapioId id = new OpcaoDoCardapioId();
		id.setIdDaOpcao(idDaOpcao);
		id.setIdDoCardapio(idDoCardapio);
		novaOpcao.setId(id);
		novaOpcao.setCardapio(cardapio);
		novaOpcao.setOpcao(opcao);
		novaOpcao.setPreco(detalhes.getPreco());
		novaOpcao.setRecomendado(detalhes.getRecomendado());
		novaOpcao.setSecao(secao);
		this.repository.save(novaOpcao);
		return buscarPor(idDoCardapio, idDaOpcao);
	}	

	@Override
	public OpcaoDoCardapio alterarPor(Integer idDoCardapio, 
			Integer idDaOpcao, DetalhesDaOpcao detalhes) {		

		Preconditions.checkArgument(detalhes.getIdDaSecao() == null, 
				"A seção da opção não pode ser alterada.");
		
		Preconditions.checkNotNull(detalhes.getStatus(), "O status é obrigatório");

		Cardapio cardapio = cardapioService.buscarPor(idDoCardapio);		

		Opcao opcao = opcaoService.buscarPor(idDaOpcao);

		Preconditions.checkArgument(opcao.getRestaurante().equals(
				cardapio.getRestaurante()), "A opção não pertence ao restaurante");

		OpcaoDoCardapio opcaoDoCardapioSalva = buscarPor(cardapio.getId(), opcao.getId());

		Preconditions.checkNotNull(opcaoDoCardapioSalva, "Não existe vinculo para a opção '" 
				+ idDaOpcao + "' no cardápio informado");		

		opcaoDoCardapioSalva.setPreco(detalhes.getPreco());
		opcaoDoCardapioSalva.setRecomendado(detalhes.getRecomendado());
		opcaoDoCardapioSalva.setStatus(detalhes.getStatus());

		this.repository.save(opcaoDoCardapioSalva);

		return buscarPor(idDoCardapio, idDaOpcao);

	}

	@Override
	public OpcaoDoCardapio buscarPor(Integer idDoCardapio, Integer idDaOpcao) {
		Cardapio cardapio = cardapioService.buscarPor(idDoCardapio);
		Opcao opcao = opcaoService.buscarPor(idDaOpcao);
		OpcaoDoCardapio opcaoDoCardapio = repository.buscarOpcaoPor(cardapio.getId(), opcao.getId());
		Preconditions.checkNotNull(opcaoDoCardapio, 
				"Não existe opção e/ou cardápio para os id's informados");
		return opcaoDoCardapio;
	}

}
