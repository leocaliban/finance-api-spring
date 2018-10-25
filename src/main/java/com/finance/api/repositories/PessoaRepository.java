package com.finance.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finance.api.model.Pessoa;

public interface  PessoaRepository extends JpaRepository<Pessoa, Long>{

}
