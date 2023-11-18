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
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import br.com.senai.cardapiosmktplaceapi.entity.enums.TipoDeCategoria;
import br.com.senai.cardapiosmktplaceapi.service.CategoriaService;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

	@Autowired
	private MapConverter converter;
	
	@Autowired
	@Qualifier("categoriaServiceProxy")
	private CategoriaService service;	

	@PostMapping
	@Transactional
	public ResponseEntity<?> inserir(
			@RequestBody 
			Categoria categoria) {
		Preconditions.checkArgument(!categoria.isPersistida(), 
				"A categoria não pode possuir id informado");
		Categoria categoriaSalva = service.salvar(categoria);
		return ResponseEntity.created(URI.create(
				"/categorias/id/" + categoriaSalva.getId())).build();
	}
	
	@Transactional
	@PutMapping
	public ResponseEntity<?> alterar(
			@RequestBody 
			Categoria categoria) {
		Preconditions.checkArgument(categoria.isPersistida(), 
				"O id da categoria é obrigatório para atualização");
		Categoria categoriaAtualizada = service.salvar(categoria);
		return ResponseEntity.ok(converter.toJsonMap(categoriaAtualizada));
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
		Categoria categoriaExcluida = service.excluirPor(id);
		return ResponseEntity.ok(converter.toJsonMap(categoriaExcluida));
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<?> buscarPor(
			@PathVariable("id") 
			Integer id) {
		Categoria categoriaEncontrada = service.buscarPor(id);
		return ResponseEntity.ok(converter.toJsonMap(categoriaEncontrada));
	}
	
	@GetMapping
	public ResponseEntity<?> listarPor(
			@RequestParam(name = "nome")
			Optional<String> filtroDeNome, 
			@RequestParam(name = "status")
			Optional<Status> filtroDeStatus, 
			@RequestParam(name = "tipo")
			Optional<TipoDeCategoria> filtroDoTipo,
			@RequestParam(name = "pagina")
			Optional<Integer> pagina){		
		Pageable paginacao = null;
		if (pagina.isPresent()) {
			paginacao = PageRequest.of(pagina.get(), 15);
		}else {
			paginacao = PageRequest.of(0, 15);
		}
		
		String nome = filtroDeNome.isPresent() ? filtroDeNome.get() : null;
		
		Status status = filtroDeStatus.isPresent() ? filtroDeStatus.get() : null;
		
		TipoDeCategoria tipo = filtroDoTipo.isPresent() ? filtroDoTipo.get() : null;
		
		Page<Categoria> categorias = service.listarPor(nome, status, tipo, paginacao);
		
		return ResponseEntity.ok(converter.toJsonList(categorias));
	}
	
}
