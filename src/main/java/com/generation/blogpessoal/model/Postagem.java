package com.generation.blogpessoal.model;

import java.time.LocalDate;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity //TRANSFORMA A CLASSE EM TABELA
@Table(name = "tb_postagens") //DEFINE O NOME PARA A TABELA
public class Postagem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //REGRA DE AUTOINCREMENTO
	private Long id;
	
	@NotBlank
	@Size(min = 3, max = 100)
	private String titulo;
	
	@NotBlank
	@Size(min = 10, max = 1000)
	private String texto;
	
	@UpdateTimestamp
	private LocalDate data;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}
	
	
	

}
