package com.example.ReservaBiblioteca.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String titulo;

    @JsonIgnore
    @OneToMany(mappedBy = "livro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Emprestimo> emprestimos = new ArrayList<>();

    @NotBlank
    private String autor;

    @NotBlank
    private String isbn;

    private String editora;
    private int ano;
    private String categoria;

    @Enumerated(EnumType.STRING)
    private Status status; // DISPONIVEL ou EMPRESTADO

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getEditora() { return editora; }
    public void setEditora(String editora) { this.editora = editora; }

    public int getAno() { return ano; }
    public void setAno(int ano) { this.ano = ano; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

}

