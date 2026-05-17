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
import java.util.Optional;

@Service
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final LivroRepository livroRepository;
    private final UsuarioRepository usuarioRepository;

    public EmprestimoService(EmprestimoRepository emprestimoRepository,
     LivroRepository livroRepository,
     UsuarioRepository usuarioRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.livroRepository = livroRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // Criar novo empréstimo
    public Emprestimo criarEmprestimo(EmprestimoDTO dto) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(dto.getUsuarioId());
        Optional<Livro> livroOpt = livroRepository.findById(dto.getLivroId());

        if (usuarioOpt.isEmpty() || livroOpt.isEmpty()) {
            throw new RuntimeException("Usuário ou Livro não encontrado!");
        }

        Livro livro = livroOpt.get();
        if (livro.getStatus() == Status.EMPRESTADO) {
            throw new RuntimeException("Livro já está emprestado!");
        }

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setUsuario(usuarioOpt.get());
        emprestimo.setLivro(livro);
        emprestimo.setDataEmprestimo(LocalDate.now());
        emprestimo.setDevolvido(false);

        // Atualiza status do livro
        livro.setStatus(Status.EMPRESTADO);
        livroRepository.save(livro);

        return emprestimoRepository.save(emprestimo);
    }

    // Finalizar devolução
    public Emprestimo finalizarEmprestimo(Long emprestimoId) {
        Optional<Emprestimo> emprestimoOpt = emprestimoRepository.findById(emprestimoId);

        if (emprestimoOpt.isEmpty()) {
            throw new RuntimeException("Empréstimo não encontrado!");
        }

        Emprestimo emprestimo = emprestimoOpt.get();
        if (emprestimo.isDevolvido()) {
            throw new RuntimeException("Este empréstimo já foi finalizado!");
        }

        emprestimo.setDevolvido(true);
        emprestimo.setDataDevolucao(LocalDate.now());

        // Atualiza status do livro
        Livro livro = emprestimo.getLivro();
        livro.setStatus(Status.DISPONIVEL);
        livroRepository.save(livro);

        return emprestimoRepository.save(emprestimo);
    }

    // Listar todos os empréstimos
    public List<Emprestimo> listarEmprestimos() {
        return emprestimoRepository.findAll();
    }
}
