package com.example.ReservaBiblioteca.service;

import com.example.ReservaBiblioteca.dto.LivroDTO;
import com.example.ReservaBiblioteca.entity.Livro;
import com.example.ReservaBiblioteca.entity.Status;
import com.example.ReservaBiblioteca.repository.LivroRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LivroService {

    private final LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    /* =========================
       LISTAR TODOS
    ========================= */

    public List<LivroDTO> listarTodos() {

        return livroRepository
                .findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /* =========================
       BUSCAR
    ========================= */

    public List<LivroDTO> buscar(String titulo, String autor, String isbn) {

        if (isbn != null && !isbn.isBlank()) {

            Livro livro = livroRepository.findByIsbn(isbn);

            return livro != null
                    ? List.of(toDTO(livro))
                    : List.of();
        }

        if (titulo != null && !titulo.isBlank()) {

            return livroRepository
                    .findByTituloContainingIgnoreCase(titulo)
                    .stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
        }

        if (autor != null && !autor.isBlank()) {

            return livroRepository
                    .findByAutorContainingIgnoreCase(autor)
                    .stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
        }

        return List.of();
    }

    /* =========================
       CADASTRAR
    ========================= */

    public LivroDTO cadastrar(LivroDTO dto) {

        Livro livro = toEntity(dto);

        Livro livroSalvo = livroRepository.save(livro);

        return toDTO(livroSalvo);
    }

    /* =========================
       EDITAR
    ========================= */

    public LivroDTO editar(Long id, LivroDTO dto) {

        Livro livro = livroRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Livro não encontrado"));

        livro.setTitulo(dto.getTitulo());
        livro.setAutor(dto.getAutor());
        livro.setIsbn(dto.getIsbn());
        livro.setEditora(dto.getEditora());
        livro.setAno(dto.getAno());
        livro.setCategoria(dto.getCategoria());

        // Atualiza status apenas se vier preenchido
        if (dto.getStatus() != null && !dto.getStatus().isBlank()) {

            livro.setStatus(
                    Status.valueOf(dto.getStatus())
            );
        }

        Livro livroAtualizado = livroRepository.save(livro);

        return toDTO(livroAtualizado);
    }

    /* =========================
       EXCLUIR
    ========================= */

    public void excluir(Long id) {

        livroRepository.deleteById(id);
    }

    /* =========================
       ENTITY -> DTO
    ========================= */

    private LivroDTO toDTO(Livro livro) {

        LivroDTO dto = new LivroDTO();

        dto.setId(livro.getId());
        dto.setTitulo(livro.getTitulo());
        dto.setAutor(livro.getAutor());
        dto.setIsbn(livro.getIsbn());
        dto.setEditora(livro.getEditora());
        dto.setAno(livro.getAno());
        dto.setCategoria(livro.getCategoria());

        if (livro.getStatus() != null) {

            dto.setStatus(livro.getStatus().name());
        }

        return dto;
    }

    /* =========================
       DTO -> ENTITY
    ========================= */

    private Livro toEntity(LivroDTO dto) {

        Livro livro = new Livro();

        livro.setTitulo(dto.getTitulo());
        livro.setAutor(dto.getAutor());
        livro.setIsbn(dto.getIsbn());
        livro.setEditora(dto.getEditora());
        livro.setAno(dto.getAno());
        livro.setCategoria(dto.getCategoria());

        // Status padrão
        livro.setStatus(Status.DISPONIVEL);

        return livro;
    }
}