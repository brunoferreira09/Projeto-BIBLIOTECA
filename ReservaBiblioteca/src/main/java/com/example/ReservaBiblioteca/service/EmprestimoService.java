package com.example.ReservaBiblioteca.service;

import com.example.ReservaBiblioteca.dto.EmprestimoDTO;
import com.example.ReservaBiblioteca.entity.Emprestimo;
import com.example.ReservaBiblioteca.entity.Livro;
import com.example.ReservaBiblioteca.entity.Status;
import com.example.ReservaBiblioteca.entity.Usuario;
import com.example.ReservaBiblioteca.repository.EmprestimoRepository;
import com.example.ReservaBiblioteca.repository.LivroRepository;
import com.example.ReservaBiblioteca.repository.UsuarioRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final LivroRepository livroRepository;
    private final UsuarioRepository usuarioRepository;

    public EmprestimoService(
            EmprestimoRepository emprestimoRepository,
            LivroRepository livroRepository,
            UsuarioRepository usuarioRepository) {

        this.emprestimoRepository = emprestimoRepository;
        this.livroRepository = livroRepository;
        this.usuarioRepository = usuarioRepository;
    }

    /* =========================
       CRIAR EMPRÉSTIMO
    ========================= */

    public Emprestimo criarEmprestimo(EmprestimoDTO dto) {

        Usuario usuario = usuarioRepository
                .findById(dto.getUsuarioId())
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado!"));

        Livro livro = livroRepository
                .findById(dto.getLivroId())
                .orElseThrow(() ->
                        new RuntimeException("Livro não encontrado!"));

        // verifica se já está emprestado
        if (livro.getStatus() == Status.EMPRESTADO) {

            throw new RuntimeException("Livro já está emprestado!");
        }

        Emprestimo emprestimo = new Emprestimo();

        emprestimo.setUsuario(usuario);
        emprestimo.setLivro(livro);
        emprestimo.setDataEmprestimo(LocalDate.now());
        emprestimo.setDevolvido(false);

        // atualiza status do livro
        livro.setStatus(Status.EMPRESTADO);

        livroRepository.save(livro);

        return emprestimoRepository.save(emprestimo);
    }

    /* =========================
       FINALIZAR DEVOLUÇÃO
    ========================= */

    public Emprestimo finalizarEmprestimo(Long emprestimoId) {

        Emprestimo emprestimo = emprestimoRepository
                .findById(emprestimoId)
                .orElseThrow(() ->
                        new RuntimeException("Empréstimo não encontrado!"));

        // verifica se já foi devolvido
        if (emprestimo.isDevolvido()) {

            throw new RuntimeException(
                    "Este empréstimo já foi finalizado!"
            );
        }

        emprestimo.setDevolvido(true);
        emprestimo.setDataDevolucao(LocalDate.now());

        // atualiza status do livro
        Livro livro = emprestimo.getLivro();

        livro.setStatus(Status.DISPONIVEL);

        livroRepository.save(livro);

        return emprestimoRepository.save(emprestimo);
    }

    /* =========================
       LISTAR EMPRÉSTIMOS
    ========================= */

    public List<Emprestimo> listarEmprestimos() {

        return emprestimoRepository.findAll();
    }
}

