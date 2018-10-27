package com.finance.api.repositories.lancamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.finance.api.model.Lancamento;
import com.finance.api.repositories.filter.LancamentoFilter;
import com.finance.api.repositories.projection.ResumoLancamento;

public interface LancamentoRepositoryQuery {

	/**
	 * Método que implementa o filtro para a busca de lançamentos. E define a
	 * paginação de lançamentos.
	 * 
	 * @param filter   classe que traz os parametros que serão filtrados.
	 * @param pageable paginação
	 * @return Páginas de lançamentos.
	 */
	public Page<Lancamento> filtrar(LancamentoFilter filter, Pageable pageable);

	/**
	 * Método que implementa o resumo do lançamento
	 * 
	 * @param filter   classe que traz os parametros que serão filtrados.
	 * @param pageable paginação
	 * @return Páginas de lançamentos resumidos.
	 */
	public Page<ResumoLancamento> resumir(LancamentoFilter filter, Pageable pageable);

}
