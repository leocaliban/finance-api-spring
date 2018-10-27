package com.finance.api.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.finance.api.model.Lancamento;
import com.finance.api.model.Pessoa;
import com.finance.api.repositories.LancamentoRepository;
import com.finance.api.repositories.filter.LancamentoFilter;
import com.finance.api.repositories.projection.ResumoLancamento;
import com.finance.api.services.exceptions.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService {

	@Autowired
	private PessoaService pessoaService;

	@Autowired
	private LancamentoRepository repository;

	public Page<Lancamento> filtrar(LancamentoFilter filter, Pageable pageable) {
		return repository.filtrar(filter, pageable);

	}

	public Lancamento buscarPorCodigo(Long codigo) {
		Lancamento lancamento = repository.findOne(codigo);

		if (lancamento == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return lancamento;
	}

	public Page<ResumoLancamento> resumirListagem(LancamentoFilter filter, Pageable pageable) {
		return repository.resumir(filter, pageable);
	}

	public Lancamento salvar(Lancamento lancamento) {
		Pessoa pessoa = pessoaService.buscarPorCodigoParaLancamento(lancamento.getPessoa().getCodigo());
		if (pessoa == null || pessoa.isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}

		Lancamento lancamentoSalvo = repository.save(lancamento);
		return lancamentoSalvo;
	}

	public Lancamento atualizar(Long codigo, Lancamento lancamento) {
		Lancamento lancamentoSalvo = buscarPorCodigo(codigo);
		if (!lancamento.getPessoa().equals(lancamentoSalvo.getPessoa())) {
			validarPessoa(lancamento);
		}
		BeanUtils.copyProperties(lancamento, lancamentoSalvo, "codigo");
		return repository.save(lancamentoSalvo);
	}

	public void remover(Long codigo) {
		repository.delete(codigo);
	}

	private void validarPessoa(Lancamento lancamento) {
		Pessoa pessoa = null;
		if (lancamento.getPessoa().getCodigo() != null) {
			pessoa = pessoaService.buscarPorCodigoParaLancamento(lancamento.getPessoa().getCodigo());
		}
		if (pessoa == null || pessoa.isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}
	}
}
