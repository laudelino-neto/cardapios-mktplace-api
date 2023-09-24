package br.com.senai.cardapiosmktplaceapi.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Preconditions;

import br.com.senai.cardapiosmktplaceapi.entity.Categoria;
import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import br.com.senai.cardapiosmktplaceapi.service.CategoriaService;
import br.com.senai.cardapiosmktplaceapi.service.RestauranteService;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private MapConverter converter;
	
	@Autowired
	@Qualifier("restauranteServiceProxy")
	private RestauranteService service;
	
	@Autowired
	@Qualifier("categoriaServiceProxy")
	private CategoriaService categoriaService;
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> inserir(
			@RequestBody 
			Restaurante restaurante) {
		Preconditions.checkArgument(!restaurante.isPersistido(), 
				"O restaurante não pode possuir id informado");
		Restaurante restauranteSalvo = service.salvar(restaurante);
		return ResponseEntity.created(URI.create(
				"/restaurantes/id/" + restauranteSalvo.getId())).build();
	}
	
	@Transactional
	@PutMapping
	public ResponseEntity<?> alterar(
			@RequestBody 
			Restaurante restaurante) {
		Preconditions.checkArgument(restaurante.isPersistido(), 
				"O id do restaurante é obrigatório para atualização");
		Restaurante restauranteAtualizado = service.salvar(restaurante);
		return ResponseEntity.ok(converter.toJsonMap(restauranteAtualizado));
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
	
	@Transactional
	@DeleteMapping("/id/{id}")
	public ResponseEntity<?> excluirPor(
			@PathVariable("id")
			Integer id){
		Restaurante restauranteExcluido = service.buscarPor(id);
		return ResponseEntity.ok(converter.toJsonMap(restauranteExcluido));
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<?> buscarPor(
			@PathVariable("id") 
			Integer id) {
		Restaurante restauranteEncontrado = service.buscarPor(id);
		return ResponseEntity.ok(converter.toJsonMap(restauranteEncontrado));
	}
	
	@GetMapping
	public ResponseEntity<?> listarPor(
			@RequestParam(name = "nome")
			String nome, 
			@RequestParam(name = "id-categoria")
			Integer idDaCategoria,
			@RequestParam(name = "pagina")
			Optional<Integer> pagina){		
		Pageable paginacao = null;
		if (pagina.isPresent()) {
			paginacao = PageRequest.of(pagina.get(), 15);
		}else {
			paginacao = PageRequest.of(0, 15);
		}
		Categoria categoria = categoriaService.buscarPor(idDaCategoria);
		Page<Restaurante> restaurantes = service.listarPor(nome, categoria, paginacao);
		return ResponseEntity.ok(converter.toJsonList(restaurantes));
	}
	
}
