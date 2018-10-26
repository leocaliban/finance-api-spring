package com.finance.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.finance.api.model.Lancamento;
import com.finance.api.repositories.LancamentoRepository;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository repository;

	public List<Lancamento> buscarTodos() {
		return repository.findAll();
	}

	public Lancamento buscarPorCodigo(Long codigo) {
		Lancamento lancamento = repository.findOne(codigo);

		if (lancamento == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return lancamento;
	}
	
	public Lancamento salvar(Lancamento lancamento) {
		Lancamento lancamentoSalvo = repository.save(lancamento);
		return lancamentoSalvo;
	}
}
