package br.com.senai.cardapiosmktplaceapi.service.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.senai.cardapiosmktplaceapi.entity.Categoria;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import br.com.senai.cardapiosmktplaceapi.entity.enums.TipoDeCategoria;
import br.com.senai.cardapiosmktplaceapi.service.CategoriaService;

@Service
public class CategoriaServiceProxy implements CategoriaService {

	@Autowired
	@Qualifier("categoriaServiceImpl")
	private CategoriaService service;
	
	@Override
	public Categoria salvar(Categoria categoria) {
		return service.salvar(categoria);
	}

	@Override
	public void atualizarStatusPor(Integer id, Status status) {
		this.service.atualizarStatusPor(id, status);
	}

	@Override
	public Page<Categoria> listarPor(String nome, Status status, 
			TipoDeCategoria tipo, Pageable paginacao) {
		return service.listarPor(nome, status, tipo, paginacao);
	}

	@Override
	public Categoria buscarPor(Integer id) {
		return service.buscarPor(id);
	}

	@Override
	public Categoria excluirPor(Integer id) {
		return service.excluirPor(id);
	}

}
