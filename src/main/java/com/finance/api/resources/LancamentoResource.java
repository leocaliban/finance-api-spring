package com.finance.api.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.finance.api.event.RecursoCriadoEvent;
import com.finance.api.model.Lancamento;
import com.finance.api.repositories.filter.LancamentoFilter;
import com.finance.api.repositories.projection.ResumoLancamento;
import com.finance.api.services.LancamentoService;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

	@Autowired
	private LancamentoService service;

	@Autowired
	private ApplicationEventPublisher publisher;

	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	@GetMapping
	public Page<Lancamento> pesquisar(LancamentoFilter filter, Pageable pageable) {
		return service.filtrar(filter, pageable);
	}

	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	@GetMapping("/{codigo}")
	public ResponseEntity<Lancamento> buscarPorCodigo(@PathVariable Long codigo) {
		Lancamento lancamento = service.buscarPorCodigo(codigo);
		return lancamento != null ? ResponseEntity.ok(lancamento) : ResponseEntity.notFound().build();
	}

	@GetMapping(params = "resumo")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public Page<ResumoLancamento> resumir(LancamentoFilter filter, Pageable pageable) {
		return service.resumirListagem(filter, pageable);
	}

	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write')")
	@PostMapping
	public ResponseEntity<Lancamento> salvar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {
		Lancamento lancamentoSalvo = service.salvar(lancamento);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
	}

	@PreAuthorize("hasAuthority('ROLE_REMOVER_LANCAMENTO') and #oauth2.hasScope('write')")
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		service.remover(codigo);
	}
}
