package com.finance.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finance.api.model.Lancamento;
import com.finance.api.repositories.lancamento.LancamentoRepositoryQuery;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery{

}
