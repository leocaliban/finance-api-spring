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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.finance.api.event.RecursoCriadoEvent;
import com.finance.api.model.Categoria;
import com.finance.api.repositories.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaRepository repository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') AND #oauth2.hasScope('read')")
	@GetMapping
	public List<Categoria> listar() {
		return repository.findAll();

	}

	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') AND #oauth2.hasScope('read')")
	@GetMapping("/{codigo}")
	public ResponseEntity<Categoria> buscarPorCodigo(@PathVariable Long codigo) {
		Categoria categoriaRecuperada = repository.findOne(codigo);

		return categoriaRecuperada != null ? ResponseEntity.ok(categoriaRecuperada) : ResponseEntity.notFound().build();
	}

	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') AND #oauth2.hasScope('write')")
	@PostMapping
	public ResponseEntity<Categoria> salvar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
		Categoria categoriaSalva = repository.save(categoria);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));

		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		repository.delete(codigo);
	}
}
