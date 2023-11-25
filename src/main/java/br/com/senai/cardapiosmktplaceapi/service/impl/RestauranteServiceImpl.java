package br.com.senai.cardapiosmktplaceapi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;

import br.com.senai.cardapiosmktplaceapi.entity.Categoria;
import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import br.com.senai.cardapiosmktplaceapi.repository.CardapiosRepository;
import br.com.senai.cardapiosmktplaceapi.repository.RestaurantesRepository;
import br.com.senai.cardapiosmktplaceapi.service.CategoriaService;
import br.com.senai.cardapiosmktplaceapi.service.RestauranteService;

@Service
public class RestauranteServiceImpl implements RestauranteService {

	@Autowired
	private RestaurantesRepository repository;
	
	@Autowired
	private CardapiosRepository cardapiosRepository;
	
	@Autowired
	@Qualifier("categoriaServiceImpl")
	private CategoriaService categoriaService;

	@Override
	public Restaurante salvar(Restaurante restaurante) {
		Restaurante outroRestaurante = repository.buscarPor(restaurante.getNome());
		if (outroRestaurante != null) {
			if (restaurante.isPersistido()) {
				Preconditions.checkArgument(outroRestaurante.equals(restaurante), 
						"O nome do restaurante já está em uso");
			}
		}

		if (restaurante.isPersistido()) {		
			Categoria categoriaSalvaDoRestaurante = categoriaService.buscarPor(
					restaurante.getCategoria().getId());
			Preconditions.checkArgument(categoriaSalvaDoRestaurante.isAtiva(), 
					"A categoria vinculada ao restaurante está inativa");
		}

		Restaurante restauranteSalvo = repository.save(restaurante);
		return restauranteSalvo;
	}

	@Override
	public void atualizarStatusPor(Integer id, Status status) {
		Restaurante restauranteEncontrado = repository.buscarPor(id);
		Preconditions.checkNotNull(restauranteEncontrado, 
				"Não existe restaurante para o id informado");
		Preconditions.checkArgument(restauranteEncontrado.getStatus() != status, 
				"O status já está salvo para a restaurante");
		this.repository.atualizarPor(id, status);
	}

	@Override
	public Page<Restaurante> listarPor(String nome, Categoria categoria, Pageable paginacao) {
		String filtroPorNome = null;
		boolean isFiltroPorNomeInformado = nome != null && !nome.isBlank();		
		if (isFiltroPorNomeInformado) {
			Preconditions.checkArgument(nome.length() >= 3, 
					"O nome deve conter ao menos 3 caracteres para listagem");
			filtroPorNome = "%" + nome + "%";
		}		
		Page<Restaurante> pagina = repository.listarPor(filtroPorNome, categoria, paginacao);
		//Remove as fotos para que a listagem possua menos dados na memória
		this.removerFotosDos(pagina.getContent());
		return pagina;
	}
	
	private void removerFotosDos(List<Restaurante> restaurantes) {
		for (Restaurante restaurante : restaurantes) {
			restaurante.setFoto(null);
		}
	}

	@Override
	public Restaurante buscarPor(Integer id) {
		Restaurante restauranteEncontrado = repository.buscarPor(id);
		Preconditions.checkNotNull(restauranteEncontrado, 
				"Não existe restaurante para o id informado");
		Preconditions.checkArgument(restauranteEncontrado.isAtivo(), 
				"O restaurante está inativo");
		return restauranteEncontrado;
	}

	@Override
	public Restaurante excluirPor(Integer id) {
		Restaurante restauranteEncontrado = buscarPor(id);
		Long qtdeDeCardapiosVinculados = cardapiosRepository.contarPor(id);
		Preconditions.checkArgument(qtdeDeCardapiosVinculados == 0, 
				"O restaurante não pode ser removido por existirem cardápios vinculados ao mesmo");
		this.repository.deleteById(id);
		return restauranteEncontrado;
	}

}
