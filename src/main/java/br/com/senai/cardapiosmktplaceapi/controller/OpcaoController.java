package br.com.senai.cardapiosmktplaceapi.controller;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
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
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Preconditions;

import br.com.senai.cardapiosmktplaceapi.entity.Opcao;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import br.com.senai.cardapiosmktplaceapi.service.CategoriaService;
import br.com.senai.cardapiosmktplaceapi.service.OpcaoService;
import br.com.senai.cardapiosmktplaceapi.service.RestauranteService;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/opcoes")
public class OpcaoController {
	
	@Autowired
	private MapConverter converter;
	
	@Autowired
	@Qualifier("opcaoServiceProxy")
	private OpcaoService service;
	
	@Autowired
	@Qualifier("categoriaServiceProxy")
	private CategoriaService categoriaService;
	
	@Autowired
	@Qualifier("restauranteServiceProxy")
	private RestauranteService restauranteService;
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> inserir(
			@RequestBody
			Opcao novaOpcao){
		Preconditions.checkArgument(!novaOpcao.isPersistida(), 
				"A opção não pode possuir id informado");
		Opcao opcaoSalva = service.salvar(novaOpcao);
		return ResponseEntity.created(URI.create(
				"/opcoes/id/" + opcaoSalva.getId())).build();
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity<?> alterar(
			@RequestBody			
			Opcao opcaoSalva){
		Preconditions.checkArgument(opcaoSalva.isPersistida(), 
				"O id da opção é obrigatório para atualização");
		Opcao opcaoAtualizada = service.salvar(opcaoSalva);
		return ResponseEntity.ok(converter.toJsonMap(opcaoAtualizada));
	}
	
	@PatchMapping("/id/{id}/status/{status}")
	@Transactional
	public ResponseEntity<?> atualizarStatusPor(
			@PathVariable("id")
			Integer id, 
			@PathVariable("status")
			Status status){
		this.service.atualizarStatusPor(id, status);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/id/{id}")
	@Transactional
	public ResponseEntity<?> excluirPor(
			@PathVariable("id")
			Integer id){
		Opcao opcaoExcluida = service.excluirPor(id);
		return ResponseEntity.ok(converter.toJsonMap(opcaoExcluida));
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<?> buscarPor(
			@PathVariable("id") 
			Integer id) {
		Opcao opcaoEncontrada = service.buscarPor(id);
		return ResponseEntity.ok(converter.toJsonMap(opcaoEncontrada));
	}
	
	@GetMapping
	public ResponseEntity<?> listarPor(
			@RequestParam(name = "nome")
			String nome, 
			@RequestParam(name = "id-restaurante")
			Optional<Integer> idDoRestaurante, 
			@RequestParam(name = "id-categoria")
			Optional<Integer> idDaCategoria,
			@RequestParam(name = "pagina")
			Optional<Integer> pagina){
		
		Pageable paginacao = null;
		
		if (pagina.isPresent()) {
			paginacao = PageRequest.of(pagina.get(), 15);
		}else {
			paginacao = PageRequest.of(0, 15);
		}
		Page<Opcao> opcoes = service.listarPor(nome, idDaCategoria.orElse(null), 
				idDoRestaurante.orElse(null), paginacao);
		return ResponseEntity.ok(converter.toJsonList(opcoes));
	}
		
	@PostMapping("/id/{id}/upload")
	public ResponseEntity<?> upload(
			@PathVariable
			Integer id,
			@RequestParam("foto")
			MultipartFile foto)throws IOException {
		Opcao opcaoEncontrada = service.buscarPor(id);		
		if (foto.getContentType() == null || !foto.getContentType().equals("image/jpeg")) {
			throw new IllegalArgumentException("O formato do arquivo é inválido. O upload aceita apenas jpg");
		}
		opcaoEncontrada.setFoto(foto.getBytes());
		this.service.salvar(opcaoEncontrada);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping(value = "/id/{id}/foto", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<?> carregarImagem(
			@PathVariable("id")
			Integer id) throws IOException{
		
		Opcao opcaoEncontrada = service.buscarPor(id);
		
		if (opcaoEncontrada.isPossuiFoto()) {			
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(opcaoEncontrada.getFoto());
		}
		
		ClassPathResource imgFile = new ClassPathResource("imagens/registro_sem_imagem.jpg");
        byte[] foto = StreamUtils.copyToByteArray(imgFile.getInputStream());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(foto);

	}
}
