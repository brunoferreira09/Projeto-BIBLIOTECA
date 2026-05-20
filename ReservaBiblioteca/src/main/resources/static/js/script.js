const API_USUARIO = "/api/usuarios";
const API_LIVRO = "/api/livros";
const API_EMPRESTIMO = "/api/emprestimos";

/* =========================
   USUÁRIOS
========================= */

async function cadastrarUsuario() {
    const usuario = {
        nome: document.getElementById("nomeUsuario").value,
        matricula: document.getElementById("matriculaUsuario").value,
        contato: document.getElementById("contatoUsuario").value
    };

    try {
        const response = await fetch(API_USUARIO, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(usuario)
        });

        if (response.ok) {
            alert("Usuário cadastrado com sucesso!");
            document.getElementById("nomeUsuario").value = "";
            document.getElementById("matriculaUsuario").value = "";
            document.getElementById("contatoUsuario").value = "";
            listarUsuarios();
        } else {
            alert("Erro ao cadastrar usuário");
        }
    } catch (error) {
        console.error(error);
        alert("Erro na requisição");
    }
}

async function listarUsuarios() {
    try {
        const response = await fetch(API_USUARIO);
        const usuarios = await response.json();
        const lista = document.getElementById("listaUsuarios");
        lista.innerHTML = "";

        usuarios.forEach(usuario => {
            lista.innerHTML += `
                <li>
                    ID: ${usuario.id} |
                    Nome: ${usuario.nome} |
                    Matrícula: ${usuario.matricula} |
                    Contato: ${usuario.contato}
                </li>
            `;
        });
    } catch (error) {
        console.error(error);
    }
}

/* ===========================
   LIVROS
=========================== */

async function cadastrarLivro() {
    const livro = {
        titulo: document.getElementById("tituloLivro").value,
        autor: document.getElementById("autorLivro").value,
        isbn: document.getElementById("isbnLivro").value,
        categoria: document.getElementById("categoriaLivro").value,
        editora: document.getElementById("editoraLivro").value,
        ano: parseInt(document.getElementById("anoLivro").value)
    };

    try {
        const response = await fetch(API_LIVRO, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(livro)
        });

        if (response.ok) {
            alert("Livro cadastrado com sucesso!");
            document.getElementById("tituloLivro").value = "";
            document.getElementById("autorLivro").value = "";
            document.getElementById("isbnLivro").value = "";
            document.getElementById("categoriaLivro").value = "";
            document.getElementById("editoraLivro").value = "";
            document.getElementById("anoLivro").value = "";
            listarLivros();
        } else {
            alert("Erro ao cadastrar livro");
        }
    } catch (error) {
        console.error(error);
        alert("Erro na requisição");
    }
}

async function listarLivros() {
    try {
        const response = await fetch(API_LIVRO);
        const livros = await response.json();
        const lista = document.getElementById("listaLivros");
        lista.innerHTML = "";

        livros.forEach(livro => {
            lista.innerHTML += `
                <li>
                    ID: ${livro.id} |
                    Título: ${livro.titulo} |
                    Autor: ${livro.autor} |
                    ISBN: ${livro.isbn} |
                    Categoria: ${livro.categoria}
                </li>
            `;
        });
    } catch (error) {
        console.error(error);
    }
}

/* ===========================
   EMPRÉSTIMOS
=========================== */

async function registrarEmprestimo() {
    const emprestimo = {
        usuarioId: parseInt(document.getElementById("usuarioId").value),
        livroId: parseInt(document.getElementById("livroId").value)
    };

    try {
        const response = await fetch(API_EMPRESTIMO, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(emprestimo)
        });

        if (response.ok) {
            alert("Empréstimo registrado com sucesso!");
            document.getElementById("usuarioId").value = "";
            document.getElementById("livroId").value = "";
            listarEmprestimos();
        } else {
            alert("Erro empréstimo em duplicidade!!!");
        }
    } catch (error) {
        console.error(error);
        alert("Erro na requisição");
    }
}

async function devolverEmprestimo() {
    const id = document.getElementById("emprestimoId").value;

    try {
        const response = await fetch(`${API_EMPRESTIMO}/${id}/devolucao`, {
            method: "PUT"
        });

        if (response.ok) {
            alert("Empréstimo devolvido com sucesso!");
            document.getElementById("emprestimoId").value = "";
            listarEmprestimos();
        } else {
            alert("Erro ao devolver empréstimo");
        }
    } catch (error) {
        console.error(error);
        alert("Erro na requisição");
    }
}

async function listarEmprestimos() {
    try {
        const response = await fetch(API_EMPRESTIMO);
        const emprestimos = await response.json();
        const lista = document.getElementById("listaEmprestimos");
        lista.innerHTML = "";

        emprestimos.forEach(e => {
            lista.innerHTML += `
                <li>
                    ID: ${e.id} |
                    Usuário: ${e.usuario?.nome} |
                    Livro: ${e.livro?.titulo} |
                    Devolvido: ${e.devolvido}
                </li>
            `;
        });
    } catch (error) {
        console.error(error);
    }
}

/* =========================
   INICIAR
========================= */

listarUsuarios();
listarLivros();
listarEmprestimos();
console.log("JS CARREGADO");
