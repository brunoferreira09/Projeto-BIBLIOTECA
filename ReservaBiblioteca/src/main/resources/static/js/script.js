const API_USUARIO = "/api/usuarios";
const API_LIVRO = "/api/livros";
const API_EMPRESTIMO = "/api/emprestimos";

/* =========================
   USUÁRIOS
========================= */
/* melhoria na UX e evita dados vazios */
async function cadastrarUsuario() {

    const usuario = {
        nome,
        matricula,
        contato
    };

    const nome = document.getElementById("nomeUsuario").value.trim();
    const matricula = document.getElementById("matriculaUsuario").value.trim();
    const contato = document.getElementById("contatoUsuario").value.trim();
    
    if (!nome || !matricula || !contato) {
        alert("Preencha todos os campos!");
        return;
    } 


    try {

        const response = await fetch(API_USUARIO, {

            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify(usuario)
        });

        console.log("STATUS:", response.status);

        if(response.ok) {

            alert("Usuário cadastrado com sucesso!");

            document.getElementById("nomeUsuario").value = "";

            document.getElementById("matriculaUsuario").value = "";

            document.getElementById("contatoUsuario").value = "";

            listarUsuarios();

        } else {

            const erro = await response.text();

            console.log(erro);

            alert("Erro ao cadastrar usuário!");
        }

    } catch(error) {

        console.error(error);

        alert("Erro na requisição!");
    }
}

async function listarUsuarios() {

    try {

        const response = await fetch(API_USUARIO);

        const usuarios = await response.json();

        const lista =
            document.getElementById("listaUsuarios");

        lista.innerHTML = "";

        usuarios.forEach(usuario => {

            lista.innerHTML += `

                <li>

                    ID: ${usuario.id} |
                    Nome: ${usuario.nome} |
                    Matrícula: ${usuario.matricula} |
                    Contato: ${usuario.contato}

                    <button
                        onclick="excluirUsuario(${usuario.id})"
                        class="btn-excluir">

                        Excluir

                    </button>

                </li>
            `;
        });

    } catch (error) {

        console.error(error);
    }
}

async function excluirUsuario(id) {

    const confirmar =
        confirm("Deseja realmente excluir este usuário?");

    if(!confirmar) {

        return;
    }

    try {

        const response = await fetch(

            `${API_USUARIO}/${id}`,

            {
                method: "DELETE"
            }
        );

        if(response.ok) {

            alert("Usuário excluído com sucesso!");

            listarUsuarios();

        } else {

            alert("Usuário não tem permissão para exclusão!");
        }

    } catch(error) {

        console.error(error);

        alert("Erro na requisição!");
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
    } finally {

        botao.disabled = false;
        botao.innerText = "Cadastrar Livro";
    } 
    const botao = event.target;

    botao.disabled = true;
    botao.innerText = "Cadastrando...";

} 
async function listarLivros() {

    try {

        const response = await fetch(API_LIVRO);

        const livros = await response.json();

        const lista =
            document.getElementById("listaLivros");

        lista.innerHTML = "";

        livros.forEach(livro => {

            lista.innerHTML += `

                <li>

                    ID: ${livro.id} |
                    Título: ${livro.titulo} |
                    Autor: ${livro.autor} |
                    ISBN: ${livro.isbn} |
                    Categoria: ${livro.categoria}

                    <button
                        onclick="excluirLivro(${livro.id})"
                        class="btn-excluir">

                        Excluir

                    </button>

                </li>
            `;
        });

    } catch (error) {

        console.error(error);
    }
}

async function excluirLivro(id) {

    const confirmar =
        confirm("Deseja realmente excluir este livro?");

    if(!confirmar) {

        return;
    }

    try {

        const response = await fetch(

            `${API_LIVRO}/${id}`,

            {
                method: "DELETE"
            }
        );

        if(response.ok) {

            alert("Livro excluído com sucesso!");

            listarLivros();

        } else {

            alert("Erro ao excluir livro!");
        }

    } catch(error) {

        console.error(error);

        alert("Erro na requisição!");
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
    const lista = document.getElementById("historicoEmprestimosLista"); // novo id
    if (!lista) return;

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


async function listarLivrosEmprestados() {
  try {
    const response = await fetch(API_LIVRO);
    const livros = await response.json();
    const lista = document.getElementById("listaLivrosEmprestados");
    if (!lista) return;

    lista.innerHTML = "";
    livros
      .filter(livro => livro.status === "EMPRESTADO") // só emprestados
      .forEach(livro => {
        lista.innerHTML += `
          <li>
            ID: ${livro.id} |
            Título: ${livro.titulo} |
            Autor: ${livro.autor} |
            ISBN: ${livro.isbn} |
            Categoria: ${livro.categoria} |
            Status: ${livro.status}
          </li>
        `;
      });
  } catch (error) {
    console.error(error);
  }
}

/* ajuste para encerrar a sessao no backend, evitar que o usuario continue autenticando e comportamento profissional */
async function logout() {

    try {

        await fetch("/logout", {
            method: "POST"
        });

    } catch(error) {

        console.error(error);
    }

    sessionStorage.clear();
    localStorage.clear();

    window.location.href = "/login.html";
}x

/* =========================
   INICIAR CONDICIONAL
========================= */

// Só chama se o elemento existir na página
if (document.getElementById("listaUsuarios")) {
    listarUsuarios();
}
if (document.getElementById("listaLivros")) {
    listarLivros();
}
if (document.getElementById("historicoEmprestimosLista")) {//ajuste chamada da funcao 
    listarEmprestimos();
}
if (document.getElementById("listaLivrosEmprestados")) { // novo ajuste
    listarLivrosEmprestados();
}

console.log("JS CARREGADO");
