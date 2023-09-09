package br.com.senai.cardapiosmktplaceapi.service.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.senai.cardapiosmktplaceapi.entity.Categoria;
import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import br.com.senai.cardapiosmktplaceapi.service.RestauranteService;

@Service
public class RestauranteServiceProxy implements RestauranteService {
	
	@Autowired
	@Qualifier("restauranteServiceImpl")
	private RestauranteService service;

	@Override
	public Restaurante salvar(Restaurante restaurante) {
		return service.salvar(restaurante);
	}

	@Override
	public void atualizarStatusPor(Integer id, Status status) {
		this.service.atualizarStatusPor(id, status);
	}

	@Override
	public Page<Restaurante> listarPor(String nome, Categoria categoria, Pageable paginacao) {
		return service.listarPor(nome, categoria, paginacao);
	}

	@Override
	public Restaurante excluirPor(Integer id) {
		return service.excluirPor(id);
	}

	@Override
	public Restaurante buscarPor(Integer id) {
		return service.buscarPor(id);
	}

}
