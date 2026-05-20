const API_USUARIO = "/api/usuarios";
const API_LIVRO = "/api/livros";
const API_EMPRESTIMO = "/api/emprestimos";

/* ===========================
   USUÁRIOS
=========================== */

async function cadastrarUsuario(){

    const usuario = {
        nome: document.getElementById("nomeUsuario").value,
        email: document.getElementById("emailUsuario").value
    };

    const response = await fetch(API_USUARIO,{
        method:"POST",
        headers:{
            "Content-Type":"application/json"
        },
        body: JSON.stringify(usuario)
    });

    if(response.ok){
        alert("Usuário cadastrado!");
        listarUsuarios();
    }
}

async function listarUsuarios(){

    const response = await fetch(API_USUARIO);

    const usuarios = await response.json();

    const lista = document.getElementById("listaUsuarios");

    lista.innerHTML = "";

    usuarios.forEach(usuario => {

        lista.innerHTML += `
            <li>
                ID: ${usuario.id} |
                Nome: ${usuario.nome} |
                Email: ${usuario.email}
            </li>
        `;
    });
}

/* ===========================
   LIVROS
=========================== */

async function cadastrarLivro(){

    const livro = {
        titulo: document.getElementById("tituloLivro").value,
        autor: document.getElementById("autorLivro").value,
        isbn: document.getElementById("isbnLivro").value
    };

    const response = await fetch(API_LIVRO,{
        method:"POST",
        headers:{
            "Content-Type":"application/json"
        },
        body: JSON.stringify(livro)
    });

    if(response.ok){
        alert("Livro cadastrado!");
        listarLivros();
    }
}

async function listarLivros(){

    const response = await fetch(API_LIVRO);

    const livros = await response.json();

    const lista = document.getElementById("listaLivros");

    lista.innerHTML = "";

    livros.forEach(livro => {

        lista.innerHTML += `
            <li>
                ID: ${livro.id} |
                ${livro.titulo} |
                ${livro.autor}
            </li>
        `;
    });
}

/* ===========================
   EMPRÉSTIMOS
=========================== */

async function registrarEmprestimo(){

    const emprestimo = {
        usuarioId: document.getElementById("usuarioId").value,
        livroId: document.getElementById("livroId").value
    };

    const response = await fetch(API_EMPRESTIMO,{
        method:"POST",
        headers:{
            "Content-Type":"application/json"
        },
        body: JSON.stringify(emprestimo)
    });

    if(response.ok){
        alert("Empréstimo registrado!");
    }else{
        alert("Erro ao registrar empréstimo");
    }
}

/* ===========================
   INICIAR LISTAS
=========================== */

listarUsuarios();
listarLivros();