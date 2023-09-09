package br.com.senai.cardapiosmktplaceapi.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;

import br.com.senai.cardapiosmktplaceapi.dto.CardapioSalvo;
import br.com.senai.cardapiosmktplaceapi.dto.NovaOpcaoDoCardapio;
import br.com.senai.cardapiosmktplaceapi.dto.NovoCardapio;
import br.com.senai.cardapiosmktplaceapi.entity.Cardapio;
import br.com.senai.cardapiosmktplaceapi.entity.Opcao;
import br.com.senai.cardapiosmktplaceapi.entity.OpcaoDoCardapio;
import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;
import br.com.senai.cardapiosmktplaceapi.entity.Secao;
import br.com.senai.cardapiosmktplaceapi.entity.composite.OpcaoDoCardapioId;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import br.com.senai.cardapiosmktplaceapi.repository.CardapiosRepository;
import br.com.senai.cardapiosmktplaceapi.repository.OpcoesRepository;
import br.com.senai.cardapiosmktplaceapi.repository.RestaurantesRepository;
import br.com.senai.cardapiosmktplaceapi.repository.SecoesRepository;
import br.com.senai.cardapiosmktplaceapi.service.CardapioService;

@Service
public class CardapioServiceImpl implements CardapioService {
	
	@Autowired
	private CardapiosRepository repository;
	
	@Autowired
	private OpcoesRepository opcoesRepository;
	
	@Autowired
	private SecoesRepository secoesRepository;
	
	@Autowired
	private RestaurantesRepository restaurantesRepository;

	@Override
	public Cardapio inserir(NovoCardapio novoCardapio) {		
		Restaurante restaurante = getRestaurantePor(novoCardapio);		
		Cardapio cardapio = new Cardapio();
		cardapio.setNome(novoCardapio.getNome());
		cardapio.setDescricao(novoCardapio.getDescricao());
		cardapio.setRestaurante(restaurante);
		cardapio.setStatus(novoCardapio.getStatus());
		Cardapio cardapioSalvo = repository.save(cardapio);		
		this.validarDuplicidadeEm(novoCardapio.getOpcoes());		
		for (NovaOpcaoDoCardapio novaOpcao : novoCardapio.getOpcoes()) {		
			Opcao opcao = getOpcaoPor(novaOpcao.getIdDaOpcao(), restaurante);			
			Secao secao = getSecaoPor(novaOpcao);						
			OpcaoDoCardapioId id = new OpcaoDoCardapioId();
			id.setIdDaOpcao(opcao.getId());
			id.setIdDoCardapio(cardapioSalvo.getId());			
			OpcaoDoCardapio opcaoDoCardapio = new OpcaoDoCardapio();
			opcaoDoCardapio.setId(id);
			opcaoDoCardapio.setCardapio(cardapioSalvo);
			opcaoDoCardapio.setOpcao(opcao);
			opcaoDoCardapio.setSecao(secao);
			opcaoDoCardapio.setPreco(novaOpcao.getPreco());
			opcaoDoCardapio.setRecomendado(novaOpcao.getRecomendado());						
			cardapioSalvo.getOpcoes().add(opcaoDoCardapio);			
		}		
		this.repository.save(cardapioSalvo);			
		return repository.buscarPor(cardapioSalvo.getId());
	}
	
	private Restaurante getRestaurantePor(NovoCardapio novoCardapio) {
		Restaurante restaurante = restaurantesRepository.buscarPor(
				novoCardapio.getRestaurante().getId());		
		Preconditions.checkNotNull(restaurante, "O restaurante '" 
				+ novoCardapio.getRestaurante().getId() + "' não foi salvo");		
		Preconditions.checkArgument(restaurante.isAtivo(), 
				"O restaurante está inativo");
		return restaurante;
	}
	
	private Secao getSecaoPor(NovaOpcaoDoCardapio novaOpcao) {		
		Preconditions.checkNotNull(novaOpcao.getSecao(), "A seção da opção '" 
				+ novaOpcao.getIdDaOpcao() + "' é obrigatória");		
		Secao secao = secoesRepository.findById(novaOpcao.getSecao().getId()).get();		
		Preconditions.checkNotNull(secao, "Não existe seção vinculada ao id '" 
				+ novaOpcao.getSecao().getId() + "'");		
		Preconditions.checkArgument(secao.isAtiva(), "A seção da opção '" 
				+ novaOpcao.getIdDaOpcao() + "' está inativa");		
		return secao;		
	}
	
	private Opcao getOpcaoPor(Integer id, Restaurante restauranteDoCardapio) {		
		Opcao opcao = opcoesRepository.buscarPor(id);		
		Preconditions.checkNotNull(opcao, "Não existe opção vinculada ao id '" + id + "'");		
		Preconditions.checkArgument(opcao.isAtiva(), "A opção '" + id + "' está inativa");		
		Preconditions.checkArgument(opcao.getRestaurante().equals(restauranteDoCardapio), 
				"A opção não pertence ao restaurante do cardápio");		
		return opcao;
	}
	
	private void validarDuplicidadeEm(List<NovaOpcaoDoCardapio> opcoesDoCardapio) {
		for (NovaOpcaoDoCardapio novaOpcao : opcoesDoCardapio) {
			int qtdeDeOcorrencias = 0;
			for (NovaOpcaoDoCardapio outraOpcao: opcoesDoCardapio) {
				if (novaOpcao.getIdDaOpcao().equals(outraOpcao.getIdDaOpcao())) {
					qtdeDeOcorrencias++;
				}
			}
			Preconditions.checkArgument(qtdeDeOcorrencias == 1, 
					"A opção '" + novaOpcao.getIdDaOpcao() + "' está duplicada no cardápio");
		}		
	}

	@Override
	public Cardapio alterar(CardapioSalvo cardapioSalvo) {
		Restaurante restaurante = restaurantesRepository.buscarPor(
				cardapioSalvo.getRestaurante().getId());
		Cardapio cardapio = repository.buscarPor(cardapioSalvo.getId());		
		cardapio.setNome(cardapioSalvo.getNome());
		cardapio.setDescricao(cardapioSalvo.getDescricao());
		cardapio.setRestaurante(restaurante);
		cardapio.setStatus(cardapioSalvo.getStatus());
		Cardapio cardapioAtualizado = repository.saveAndFlush(cardapio);
		return buscarPor(cardapioAtualizado.getId());
	}
	
	private void atualizarPrecosDas(List<OpcaoDoCardapio> opcoesDoCardapio) {
		for (OpcaoDoCardapio opcaoDoCardapio : opcoesDoCardapio) {
			if (opcaoDoCardapio.getOpcao().isEmPromocao()) {
				BigDecimal divisor = new BigDecimal(100);
				
				BigDecimal percentualDeDesconto = opcaoDoCardapio.getOpcao().getPercentualDeDesconto(); 
				
				BigDecimal valorDescontado = opcaoDoCardapio.getPreco().multiply(
						percentualDeDesconto.divide(divisor));
				
				BigDecimal preco = opcaoDoCardapio.getPreco().subtract(
						valorDescontado).setScale(2, RoundingMode.CEILING);
				
				opcaoDoCardapio.setPreco(preco);
				
			}
		}
	}

	@Override
	public Page<Cardapio> listarPor(Restaurante restaurante, Pageable paginacao) {
		Page<Cardapio> cardapios = repository.listarPor(restaurante, paginacao);
		for (Cardapio cardapio : cardapios.getContent()) {
			this.atualizarPrecosDas(cardapio.getOpcoes());
		}
		return cardapios;
	}	
	
	@Override
	public Cardapio buscarPor(Integer id) {
		Cardapio cardapioEncontrado = repository.buscarPor(id);
		Preconditions.checkNotNull(cardapioEncontrado, 
				"Não foi encontrado cardápio para o id informado");
		Preconditions.checkArgument(cardapioEncontrado.isAtivo(), 
				"O cardápio está inativo");
		this.atualizarPrecosDas(cardapioEncontrado.getOpcoes());
		return cardapioEncontrado;
	}

	@Override
	public void atualizarStatusPor(Integer id, Status status) {
		Cardapio cardapio = buscarPor(id);
		Preconditions.checkArgument(cardapio.getStatus() != status, 
				"O status informado já foi salvo anteriormente");
		this.repository.atualizarPor(id, status);
	}

}
