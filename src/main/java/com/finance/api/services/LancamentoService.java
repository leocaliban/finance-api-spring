package com.finance.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
}
