package com.example.ReservaBiblioteca.controller;

import com.example.ReservaBiblioteca.dto.LivroDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.ReservaBiblioteca.service.LivroService;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/livros")
public class LivroController {
    private final LivroService livroService;

    public LivroController(LivroService livroService) { this.livroService = livroService; }

    @GetMapping 
    public List<LivroDTO> listar() { return livroService.listarTodos(); }

    @GetMapping("/buscar")
    public List<LivroDTO> buscar(@RequestParam String titulo, @RequestParam String autor, @RequestParam String isbn) {
        return livroService.buscar(titulo, autor, isbn);
    }

    @PostMapping public ResponseEntity<LivroDTO> cadastrar(@Valid @RequestBody LivroDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(livroService.cadastrar(dto));
    }

    @PutMapping("/{id}") public ResponseEntity<LivroDTO> editar(@PathVariable Long id, @Valid @RequestBody LivroDTO dto) {
        return ResponseEntity.ok(livroService.editar(id, dto));
    }

    @DeleteMapping("/{id}") public ResponseEntity<Void> excluir(@PathVariable Long id) {
        livroService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}