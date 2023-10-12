package br.com.senai.cardapiosmktplaceapi.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.senai.cardapiosmktplaceapi.dto.CardapioSalvo;
import br.com.senai.cardapiosmktplaceapi.dto.NovoCardapio;
import br.com.senai.cardapiosmktplaceapi.entity.Cardapio;
import br.com.senai.cardapiosmktplaceapi.entity.OpcaoDoCardapio;
import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import br.com.senai.cardapiosmktplaceapi.service.CardapioService;
import br.com.senai.cardapiosmktplaceapi.service.RestauranteService;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/cardapios")
public class CardapioController {
	
	@Autowired
	@Qualifier("cardapioServiceProxy")
	private CardapioService service;
	
	@Autowired
	@Qualifier("restauranteServiceProxy")
	private RestauranteService restauranteService;
	
	private Map<String, Object> converter(Cardapio cardapio){		
		Map<String, Object> cardapioMap = new HashMap<String, Object>();
		cardapioMap.put("id", cardapio.getId());
		cardapioMap.put("nome", cardapio.getNome());
		cardapioMap.put("descricao", cardapio.getDescricao());
		cardapioMap.put("status", cardapio.getStatus());		
		Map<String, Object> restauranteMap = new HashMap<String, Object>();
		restauranteMap.put("id", cardapio.getRestaurante().getId());
		restauranteMap.put("nome", cardapio.getRestaurante().getNome());
		cardapioMap.put("restaurante", restauranteMap);
		List<Map<String, Object>> opcoesMap = new ArrayList<Map<String, Object>>();
		for (OpcaoDoCardapio opcaoDoCardapio : cardapio.getOpcoes()) {
			Map<String, Object> opcaoMap = new HashMap<String, Object>();
			opcaoMap.put("id", opcaoDoCardapio.getOpcao().getId());
			opcaoMap.put("nome", opcaoDoCardapio.getOpcao().getNome());
			opcaoMap.put("promocao", opcaoDoCardapio.getOpcao().getPromocao());
			opcaoMap.put("recomendado", opcaoDoCardapio.getRecomendado());
			opcaoMap.put("preco", opcaoDoCardapio.getPreco());			
			Map<String, Object> secaoMap = new HashMap<String, Object>();
			secaoMap.put("id", opcaoDoCardapio.getSecao().getId());
			secaoMap.put("nome", opcaoDoCardapio.getSecao().getNome());
			opcaoMap.put("secao", secaoMap);
			opcoesMap.add(opcaoMap);
		}		
		cardapioMap.put("opcoes", opcoesMap);		
		return cardapioMap;

	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> inserir(
			@RequestBody 
			NovoCardapio novoCardapio) {
		Cardapio cardapioSalvo = service.inserir(novoCardapio);		
		return ResponseEntity.created(URI.create(
				"/cardapios/id/" + cardapioSalvo.getId())).build();
	}
	
	@Transactional
	@PutMapping
	public ResponseEntity<?> alterar(
			@RequestBody 
			CardapioSalvo cardapioSalvo) {
		Cardapio cardapioAtualizado = service.alterar(cardapioSalvo);		
		return ResponseEntity.ok(converter(cardapioAtualizado));
	}
	
	@Transactional
	@PatchMapping("/id/{id}/status/{status}")
	public ResponseEntity<?> atualizarStatusPor(
			@PathVariable("id")
			Integer id, 
			@PathVariable("status")
			Status status){
		this.service.atualizarStatusPor(id, status);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<?> buscarPor(
			@PathVariable("id") 
			Integer id) {
		Cardapio cardapioEncontrado = service.buscarPor(id);
		return ResponseEntity.ok(converter(cardapioEncontrado));
	}
	
	@GetMapping
	public ResponseEntity<?> listarPor(
			@RequestParam(name = "id-restaurante")
			Integer idDoRestaurante,					
			@RequestParam(name = "pagina")
			Optional<Integer> pagina){		
		Pageable paginacao = null;
		if (pagina.isPresent()) {
			paginacao = PageRequest.of(pagina.get(), 15);
		}else {
			paginacao = PageRequest.of(0, 15);
		}
		Restaurante restaurante = restauranteService.buscarPor(idDoRestaurante); 
		Page<Cardapio> page = service.listarPor(restaurante, paginacao);
		Map<String, Object> pageMap = new HashMap<String, Object>();
		pageMap.put("paginaAtual", page.getNumber());
		pageMap.put("totalDeItens", page.getTotalElements());
		pageMap.put("totalDePaginas", page.getTotalPages());
		List<Map<String, Object>> listagem = new ArrayList<Map<String, Object>>();
		for (Cardapio cardapio : page.getContent()) {
			listagem.add(converter(cardapio));
		}
		pageMap.put("listagem", listagem);
		return ResponseEntity.ok(pageMap);
	}	
	
}
