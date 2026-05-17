package com.example.ReservaBiblioteca.controller;

import com.example.ReservaBiblioteca.dto.EmprestimoDTO;
import com.example.ReservaBiblioteca.entity.Emprestimo;
import com.example.ReservaBiblioteca.service.EmprestimoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*") 
@RestController
@RequestMapping("/api/emprestimos")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @PostMapping
    public ResponseEntity<Emprestimo> registrar(@RequestBody EmprestimoDTO dto) {
        Emprestimo emprestimo = emprestimoService.criarEmprestimo(dto);
        return ResponseEntity.ok(emprestimo);
    }

    @PutMapping("/{id}/devolucao")
    public ResponseEntity<Emprestimo> devolver(@PathVariable Long id) {
        Emprestimo emprestimo = emprestimoService.finalizarEmprestimo(id);
        return ResponseEntity.ok(emprestimo);
    }
}
