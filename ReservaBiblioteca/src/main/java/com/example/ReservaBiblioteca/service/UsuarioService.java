package com.example.ReservaBiblioteca.service;

import com.example.ReservaBiblioteca.dto.UsuarioDTO;
import com.example.ReservaBiblioteca.entity.Usuario;
import com.example.ReservaBiblioteca.repository.UsuarioRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {

        this.usuarioRepository = usuarioRepository;
    }

    /* =========================
       LISTAR TODOS
    ========================= */

    public List<UsuarioDTO> listarTodos() {

        return usuarioRepository
                .findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /* =========================
       CADASTRAR
    ========================= */

    public UsuarioDTO cadastrar(UsuarioDTO dto) {

        Usuario usuario = toEntity(dto);

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return toDTO(usuarioSalvo);
    }

    /* =========================
       EDITAR
    ========================= */

    public UsuarioDTO editar(Long id, UsuarioDTO dto) {

        Usuario usuario = usuarioRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado"));

        usuario.setNome(dto.getNome());
        usuario.setMatricula(dto.getMatricula());
        usuario.setContato(dto.getContato());

        Usuario usuarioAtualizado = usuarioRepository.save(usuario);

        return toDTO(usuarioAtualizado);
    }

    /* =========================
       EXCLUIR
    ========================= */

    public void excluir(Long id) {

        usuarioRepository.deleteById(id);
    }

    /* =========================
       ENTITY -> DTO
    ========================= */

    private UsuarioDTO toDTO(Usuario usuario) {

        UsuarioDTO dto = new UsuarioDTO();

        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setMatricula(usuario.getMatricula());

        // evita null
        if (usuario.getContato() != null) {

            dto.setContato(usuario.getContato());
        }

        return dto;
    }

    /* =========================
       DTO -> ENTITY
    ========================= */

    private Usuario toEntity(UsuarioDTO dto) {

        Usuario usuario = new Usuario();

        usuario.setNome(dto.getNome());
        usuario.setMatricula(dto.getMatricula());
        usuario.setContato(dto.getContato());

        return usuario;
    }
}