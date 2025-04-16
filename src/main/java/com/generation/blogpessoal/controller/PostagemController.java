package com.generation.blogpessoal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/postagens")
@CrossOrigin(origins = "*", allowedHeaders = "*") // libera origens q vai acessar a  controller
public class PostagemController {
	
	@Autowired //INJECAO DE DEPENDENCIAS
	private PostagemRepository postagemRepository;
	
	@GetMapping
	public ResponseEntity<List<Postagem>> getAll() {
		//RETORNA A LISTA DE POSTAGENS
		return ResponseEntity.ok (postagemRepository.findAll());
	}

	@GetMapping("/{id}") 
	public ResponseEntity<Postagem> getById(@PathVariable Long id) {
		//BUSCA A POSTAGEM PELO ID
		return postagemRepository.findById(id)
				//SE ENCONTRADA RETORNA STATUS OK E A POSTAGEM
				.map(resposta -> ResponseEntity.ok(resposta))
				//SE NAO ENCONTRADA RETORNA STATUS NOT FOUND
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo) {
		//BUSCA AS POSTAGEM E IGNORA MAIUCULAS E MINUSCULAS
		return ResponseEntity.ok (postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
	}
	 
	//METODO CADASTRAR
	@PostMapping
	public ResponseEntity<Postagem> post(@Valid @RequestBody Postagem postagem) {
		//SALVA A POSTAGEM
		return ResponseEntity.status(HttpStatus.CREATED).body(postagemRepository.save(postagem));
	}
	
	
	@PutMapping
	public ResponseEntity<Postagem> put(@Valid @RequestBody Postagem postagem) {
		//BUSCA A POSTAGEM PELO ID
		return postagemRepository.findById(postagem.getId())
				//SE ENCONTRA, RETORNA STATUS OK E SALVA A POSTAGEM
				.map(ResponseStatus -> ResponseEntity.status(HttpStatus.OK)
						.body(postagemRepository.save(postagem)))
				//SE NAO ENCONTRADA, RETORNA STATUS NOT FOUND
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		//BUSCA A POSTAGEM PELO ID
		Optional<Postagem> postagem = postagemRepository.findById(id);
		
		//SE NAO ENCONTRADA LANÇA EXCECAO NOT FOUND
		if(postagem.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		//SE ENCONTRADA EXCLUI A POSTAGEM
		postagemRepository.deleteById(id);
	}
	
	
	
}
