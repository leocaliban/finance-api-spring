package com.finance.api.resources;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.finance.api.event.RecursoCriadoEvent;
import com.finance.api.model.Pessoa;
import com.finance.api.services.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

	@Autowired
	private PessoaService service;

	@Autowired
	private ApplicationEventPublisher publisher;

	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') AND #oauth2.hasScope('read')")
	@GetMapping
	public List<Pessoa> buscarTodos() {
		return service.buscarTodos();
	}

	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') AND #oauth2.hasScope('read')")
	@GetMapping("/{codigo}")
	public ResponseEntity<Pessoa> buscarPorCodigo(@PathVariable Long codigo) {
		Pessoa pessoa = service.buscarPorCodigo(codigo);

		return pessoa != null ? ResponseEntity.ok(pessoa) : ResponseEntity.notFound().build();
	}

	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') AND #oauth2.hasScope('write')")
	@PostMapping
	public ResponseEntity<Pessoa> salvar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
		Pessoa pessoaSalva = service.salvar(pessoa);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getCodigo()));

		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
	}

	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') AND #oauth2.hasScope('write')")
	@PutMapping("/{codigo}")
	public ResponseEntity<Pessoa> editar(@PathVariable Long codigo, @Valid @RequestBody Pessoa pessoa) {
		Pessoa pessoaSalva = service.atualizar(codigo, pessoa);
		return ResponseEntity.ok(pessoaSalva);
	}

	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') AND #oauth2.hasScope('write')")
	@PutMapping("/{codigo}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarPropriedadeAtivo(@PathVariable Long codigo, @RequestBody Boolean ativo) {
		service.atualizarPropriedadeAtivo(codigo, ativo);
	}

	@PreAuthorize("hasAuthority('ROLE_REMOVER_PESSOA') AND #oauth2.hasScope('write')")
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		service.remover(codigo);
	}
}
