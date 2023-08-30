package br.com.senai.cardapiosmktplaceapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;

import br.com.senai.cardapiosmktplaceapi.entity.Categoria;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import br.com.senai.cardapiosmktplaceapi.entity.enums.TipoDeCategoria;
import br.com.senai.cardapiosmktplaceapi.repository.CategoriasRepository;
import br.com.senai.cardapiosmktplaceapi.service.CategoriaService;
import jakarta.transaction.Transactional;

@Service
public class CategoriaServiceImpl implements CategoriaService {

	@Autowired
	private CategoriasRepository repository;
	
	@Override
	public Categoria salvar(Categoria categoria) {		
		Categoria outraCategoria = repository.buscarPor(categoria.getNome());		
		if (outraCategoria != null) {
			if (categoria.isPersistida()) {
				Preconditions.checkArgument(outraCategoria.equals(categoria), 
						"O nome da categoria já está em uso");
			}
		}		
		Categoria categoriaSalva = repository.save(categoria);		
		return categoriaSalva;
	}

	@Override
	@Transactional
	public void atualizarStatusPor(Integer id, Status status) {
		Categoria categoriaEncontrada = repository.buscarPor(id);
		Preconditions.checkNotNull(categoriaEncontrada, 
				"Não existe categoria para o id informado");
		Preconditions.checkArgument(categoriaEncontrada.getStatus() != status, 
				"O status já está salvo para a categoria");
		this.repository.atualizarPor(id, status);
	}

	@Override
	public Page<Categoria> listarPor(String nome, Status status, TipoDeCategoria tipo, Pageable paginacao) {
		return repository.listarPor(nome + "%", status, tipo, paginacao);
	}

	@Override
	public Categoria buscarPor(Integer id) {
		Categoria categoriaEncontrada = repository.buscarPor(id);
		Preconditions.checkNotNull(categoriaEncontrada, 
				"Não existe categoria para o id informado");
		Preconditions.checkArgument(categoriaEncontrada.isAtiva(), 
				"A categoria está inativa");
		return categoriaEncontrada;
	}
	
	@Override
	public Categoria excluirPor(Integer id) {
		Categoria categoriaEncontrada = buscarPor(id);		
		//TODO Contar numero de restaurantes pelo id da categoria
		this.repository.deleteById(id);
		return categoriaEncontrada;
	}

}
