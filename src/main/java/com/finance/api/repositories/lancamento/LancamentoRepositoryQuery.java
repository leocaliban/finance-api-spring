package com.finance.api.repositories.lancamento;

import java.util.List;

import com.finance.api.model.Lancamento;
import com.finance.api.repositories.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {

	public List<Lancamento> filtrar(LancamentoFilter filter);

}
