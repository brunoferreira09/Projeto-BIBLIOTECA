package com.example.ReservaBiblioteca.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import com.example.ReservaBiblioteca.dto.UsuarioDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.ReservaBiblioteca.service.UsuarioService;
import jakarta.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<UsuarioDTO> listar() {
        return usuarioService.listarTodos();
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> cadastrar(@Valid @RequestBody UsuarioDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usuarioService.cadastrar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> editar(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioDTO dto) {

        return ResponseEntity.ok(usuarioService.editar(id, dto));
    }

@PreAuthorize("hasRole('ADMIN')")
@DeleteMapping("/{id}")
public ResponseEntity<Void> excluir(@PathVariable Long id ) {

    usuarioService.excluir(id);

    return ResponseEntity.noContent().build();
}

}
