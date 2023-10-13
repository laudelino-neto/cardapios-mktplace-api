package br.com.senai.cardapiosmktplaceapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;

import br.com.senai.cardapiosmktplaceapi.entity.Categoria;
import br.com.senai.cardapiosmktplaceapi.entity.Opcao;
import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import br.com.senai.cardapiosmktplaceapi.repository.OpcoesDoCardapioRepository;
import br.com.senai.cardapiosmktplaceapi.repository.OpcoesRepository;
import br.com.senai.cardapiosmktplaceapi.service.CategoriaService;
import br.com.senai.cardapiosmktplaceapi.service.OpcaoService;
import br.com.senai.cardapiosmktplaceapi.service.RestauranteService;

@Service
public class OpcaoServiceImpl implements OpcaoService{

	@Autowired
	private OpcoesRepository repository;
	
	@Autowired
	@Qualifier("categoriaServiceImpl")
	private CategoriaService categoriaService;
	
	@Autowired
	@Qualifier("restauranteServiceImpl")
	private RestauranteService restauranteService;
	
	@Autowired
	private OpcoesDoCardapioRepository opcoesDoCardapioRepository;
	
	@Override
	public Opcao salvar(Opcao opcao) {
		Opcao outraOpcao = repository.buscarPor(opcao.getNome(), opcao.getRestaurante());
		if (outraOpcao != null) {
			if (opcao.isPersistida()) {
				Preconditions.checkArgument(outraOpcao.equals(opcao), 
						"O nome da opção já está em uso neste restaurante");
			}
		}
		
		Categoria categoriaDaOpcao = categoriaService.buscarPor(opcao.getCategoria().getId());
		
		opcao.setCategoria(categoriaDaOpcao);
		
		Restaurante restauranteDaOpcao = restauranteService.buscarPor(opcao.getRestaurante().getId());
		
		opcao.setRestaurante(restauranteDaOpcao);
		
		if (opcao.isEmPromocao()) {
			Preconditions.checkArgument(opcao.getPercentualDeDesconto().doubleValue() > 0, 
					"O percentual de desconto da opção é obrigatório");
		}else {
			opcao.setPercentualDeDesconto(null);
		}
		
		Opcao opcaoSalva = repository.save(opcao);
		
		return opcaoSalva;

	}
	
	@Override
	public Opcao buscarPor(Integer id) {
		Opcao opcaoEncontrada = repository.buscarPor(id);
		Preconditions.checkNotNull(opcaoEncontrada, 
				"Não existe opção para o id informado");
		Preconditions.checkArgument(opcaoEncontrada.isAtiva(), 
				"A opção está inativa");
		return opcaoEncontrada;
	}

	@Override
	public void atualizarStatusPor(Integer id, Status status) {		
		Opcao opcaoEncontrada = repository.buscarPor(id);
		Preconditions.checkNotNull(opcaoEncontrada, 
				"Não existe opção para o id informado");
		Preconditions.checkArgument(opcaoEncontrada.getStatus() != status, 
				"O status já está salvo para a opção");
		this.repository.atualizarPor(id, status);
	}

	@Override
	public Page<Opcao> listarPor(String nome, Integer idDaCategoria, 
			Integer idDoRestaurante, Pageable paginacao) {
		Preconditions.checkArgument(idDaCategoria != null || idDoRestaurante != null, 
				"A categoria ou restaurante são obrigatórios");		
		return repository.listarPor(nome + "%", idDaCategoria, idDoRestaurante, paginacao);
	}

	@Override
	public Opcao excluirPor(Integer id) {		
		Opcao opcaoEncontrada = buscarPor(id);
		Long qtdeDeOpcoesVinculadas = opcoesDoCardapioRepository.contarPor(id);
		Preconditions.checkArgument(qtdeDeOpcoesVinculadas == 0, 
				"A opção não pode ser removida por existirem cardápios vinculados a mesma");
		this.repository.deleteById(id);
		return opcaoEncontrada;
	}

}
