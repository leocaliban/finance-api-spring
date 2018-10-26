package com.finance.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.finance.api.model.Lancamento;
import com.finance.api.model.Pessoa;
import com.finance.api.repositories.LancamentoRepository;
import com.finance.api.services.exceptions.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService {

	@Autowired
	private PessoaService pessoaService;

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
		Pessoa pessoa = pessoaService.buscarPorCodigoParaLancamento(lancamento.getPessoa().getCodigo());
		if (pessoa == null || pessoa.isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}

		Lancamento lancamentoSalvo = repository.save(lancamento);
		return lancamentoSalvo;
	}
}
