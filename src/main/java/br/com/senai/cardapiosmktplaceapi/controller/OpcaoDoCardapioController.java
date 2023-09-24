package br.com.senai.cardapiosmktplaceapi.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.senai.cardapiosmktplaceapi.dto.DetalhesDaOpcao;
import br.com.senai.cardapiosmktplaceapi.entity.OpcaoDoCardapio;
import br.com.senai.cardapiosmktplaceapi.service.OpcaoDoCardapioService;

@RestController
@RequestMapping("/opcoes-cardapio")
public class OpcaoDoCardapioController {

	@Autowired
	@Qualifier("opcaoDoCardapioServiceProxy")
	private OpcaoDoCardapioService service;
	
	private Map<String, Object> converter(OpcaoDoCardapio opcaoDoCardapio){
		Map<String, Object> opcaoDoCardapioMap = new HashMap<String, Object>();
		opcaoDoCardapioMap.put("id", opcaoDoCardapio.getOpcao().getId());
		opcaoDoCardapioMap.put("nome", opcaoDoCardapio.getOpcao().getNome());
		opcaoDoCardapioMap.put("preco", opcaoDoCardapio.getPreco());
		opcaoDoCardapioMap.put("promocao", opcaoDoCardapio.getOpcao().getPromocao());
		opcaoDoCardapioMap.put("recomendado", opcaoDoCardapio.getRecomendado());
		Map<String, Object> secaoMap = new HashMap<String, Object>();
		secaoMap.put("id", opcaoDoCardapio.getSecao().getId());
		secaoMap.put("nome", opcaoDoCardapio.getSecao().getNome());
		opcaoDoCardapioMap.put("secao", secaoMap);
		return opcaoDoCardapioMap;
	}
	
	@PostMapping("/cardapio/{id-cardapio}/opcao/{id-opcao}")
	public ResponseEntity<?> inserirPor(
			@PathVariable("id-cardapio")
			Integer idDoCardapio, 
			@PathVariable("id-opcao")
			Integer idDaOpcao, 
			@RequestBody
			DetalhesDaOpcao detalhes){
		OpcaoDoCardapio opcaoSalva = service.inserirPor(idDoCardapio, idDaOpcao, detalhes);
		return ResponseEntity.created(URI.create("/cardapio/" + opcaoSalva.getCardapio().getId() 
				+ "/opcao/" + opcaoSalva.getOpcao().getId())).build();
	}
	
	@PutMapping("/cardapio/{id-cardapio}/opcao/{id-opcao}")
	public ResponseEntity<?> alterarPor(
			@PathVariable("id-cardapio")
			Integer idDoCardapio, 
			@PathVariable("id-opcao")
			Integer idDaOpcao, 
			@RequestBody
			DetalhesDaOpcao detalhes){
		OpcaoDoCardapio opcaoAtualizada = service.alterarPor(idDoCardapio, idDaOpcao, detalhes);
		return ResponseEntity.ok(converter(opcaoAtualizada));
	}
	
	@GetMapping("/cardapio/{id-cardapio}/opcao/{id-opcao}")
	public ResponseEntity<?> buscarPor(
			@PathVariable("id-cardapio")
			Integer idDoCardapio, 
			@PathVariable("id-opcao")
			Integer idDaOpcao){
		OpcaoDoCardapio opcaoEncontrada = service.buscarPor(idDoCardapio, idDaOpcao);
		return ResponseEntity.ok(converter(opcaoEncontrada));
	}
	
}
