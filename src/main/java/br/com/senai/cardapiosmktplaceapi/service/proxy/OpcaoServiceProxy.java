package br.com.senai.cardapiosmktplaceapi.service.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.senai.cardapiosmktplaceapi.entity.Opcao;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import br.com.senai.cardapiosmktplaceapi.service.OpcaoService;

@Service
public class OpcaoServiceProxy implements OpcaoService{

	@Autowired
	@Qualifier("opcaoServiceImpl")
	private OpcaoService service;
	
	@Override
	public Opcao salvar(Opcao opcao) {
		return service.salvar(opcao);
	}
	
	@Override
	public Opcao buscarPor(Integer id) {
		return service.buscarPor(id);
	}

	@Override
	public void atualizarStatusPor(Integer id, Status status) {		
		this.service.atualizarStatusPor(id, status);
	}

	@Override
	public Page<Opcao> listarPor(String nome, Integer idDaCategoria, 
			Integer idDoRestaurante, Pageable paginacao) { 			
		return service.listarPor(nome, idDaCategoria, idDoRestaurante, paginacao);
	}

	@Override
	public Opcao excluirPor(Integer id) {
		return service.excluirPor(id);
	}
		
}
