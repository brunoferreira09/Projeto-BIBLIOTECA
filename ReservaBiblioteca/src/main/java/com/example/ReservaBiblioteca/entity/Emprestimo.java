package com.example.ReservaBiblioteca.entity;



import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private Livro livro;

    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;
    private boolean devolvido = false;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Livro getLivro() { return livro; }
    public void setLivro(Livro livro) { this.livro = livro; }

    public LocalDate getDataEmprestimo() { return dataEmprestimo; }
    public void setDataEmprestimo(LocalDate dataEmprestimo) { this.dataEmprestimo = dataEmprestimo; }

    public LocalDate getDataDevolucao() { return dataDevolucao; }
    public void setDataDevolucao(LocalDate dataDevolucao) { this.dataDevolucao = dataDevolucao; }

    public boolean isDevolvido() { return devolvido; }
    public void setDevolvido(boolean devolvido) { this.devolvido = devolvido; }
}
