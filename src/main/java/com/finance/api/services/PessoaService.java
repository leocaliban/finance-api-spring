package com.finance.api.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.finance.api.model.Pessoa;
import com.finance.api.repositories.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository repository;

	public List<Pessoa> buscarTodos() {
		return repository.findAll();
	}

	public Pessoa buscarPorCodigo(Long codigo) {
		Pessoa pessoa = repository.findOne(codigo);
		
		if (pessoa == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		return pessoa;
	}
	
	public Pessoa salvar(Pessoa pessoa) {
		Pessoa pessoaSalva = repository.save(pessoa);
		return pessoaSalva;
	}

	public Pessoa atualizar(Long codigo, Pessoa pessoa) {
		Pessoa pessoaSalva = buscarPorCodigo(codigo);

		// Propriedade do spring que aplica os dados atualizados 'pessoa' na entidade
		// que foi editada 'pessoaSalva' ignorando o 'codigo'
		BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
		return repository.save(pessoaSalva);
	}
	
	public void atualizarPropriedadeAtivo(Long codigo, Boolean ativo) {
		Pessoa pessoaSalva = buscarPorCodigo(codigo);
		
		pessoaSalva.setAtivo(ativo);
		repository.save(pessoaSalva);
	}
	
	public void remover(Long codigo) {
		repository.delete(codigo);
	}
}
