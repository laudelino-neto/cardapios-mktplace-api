package br.com.senai.cardapiosmktplaceapi.service.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.senai.cardapiosmktplaceapi.dto.DetalhesDaOpcao;
import br.com.senai.cardapiosmktplaceapi.entity.OpcaoDoCardapio;
import br.com.senai.cardapiosmktplaceapi.service.OpcaoDoCardapioService;

@Service
public class OpcaoDoCardapioServiceProxy implements OpcaoDoCardapioService {

	@Autowired
	@Qualifier("opcaoDoCardapioServiceImpl")
	private OpcaoDoCardapioService service;
	
	@Override
	public OpcaoDoCardapio inserirPor(Integer idDoCardapio, 
			Integer idDaOpcao, DetalhesDaOpcao detalhes) {
		return service.inserirPor(idDoCardapio, idDaOpcao, detalhes);
	}

	@Override
	public OpcaoDoCardapio alterarPor(Integer idDoCardapio, 
			Integer idDaOpcao, DetalhesDaOpcao detalhes) {
		return service.alterarPor(idDoCardapio, idDaOpcao, detalhes);
	}

	@Override
	public OpcaoDoCardapio buscarPor(Integer idDoCardapio, Integer idDaOpcao) {
		return service.buscarPor(idDoCardapio, idDaOpcao);
	}

}
