package com.finance.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.finance.api.model.Categoria;
import com.finance.api.repositories.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;

	public List<Categoria> buscarTodos() {
		return repository.findAll();
	}

	public Categoria buscarPorCodigoParaLancamento(Long codigo) {
		Categoria categoria = repository.findOne(codigo);
		return categoria;
	}

	public Categoria buscarPorCodigo(Long codigo) {
		Categoria categoria = repository.findOne(codigo);

		if (categoria == null) {
			throw new EmptyResultDataAccessException(1);
		}

		return categoria;
	}

	public Categoria salvar(Categoria categoria) {
		Categoria categoriaSalva = repository.save(categoria);
		return categoriaSalva;
	}

	public void remover(Long codigo) {
		repository.delete(codigo);
	}

}
