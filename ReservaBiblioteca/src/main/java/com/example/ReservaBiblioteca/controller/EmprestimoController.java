package com.example.ReservaBiblioteca.controller;

import com.example.ReservaBiblioteca.dto.EmprestimoDTO;
import com.example.ReservaBiblioteca.entity.Emprestimo;
import com.example.ReservaBiblioteca.service.EmprestimoService;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/emprestimos")

public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    // Construtor para injeção de dependência
    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    // Endpoint para registrar um novo empréstimo
    @PostMapping
    public ResponseEntity<Emprestimo> registrar(@RequestBody @Valid EmprestimoDTO dto) {

        Emprestimo emprestimo = emprestimoService.criarEmprestimo(dto);

        return ResponseEntity.ok(emprestimo);
    }

    // Endpoint para devolver/finalizar um empréstimo
    @PutMapping("/{id}/devolucao")
    public ResponseEntity<Emprestimo> devolver(@PathVariable Long id) {

        Emprestimo emprestimo = emprestimoService.finalizarEmprestimo(id);

        return ResponseEntity.ok(emprestimo);
    }

    // Endpoint para listar todos os empréstimos
    @GetMapping
    public ResponseEntity<List<Emprestimo>> listar() {
        List<Emprestimo> emprestimos = emprestimoService.listarEmprestimos();
        return ResponseEntity.ok(emprestimos);
    }
}