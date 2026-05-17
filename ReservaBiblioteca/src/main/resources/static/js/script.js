const API_URL = "http://localhost:8080/api";
const CREDENTIALS = btoa("user:user123");

// Função auxiliar para gerar headers padronizados
const getHeaders = (includeJson = false) => {
    const headers = {
        "Authorization": `Basic ${CREDENTIALS}`
    };
    if (includeJson) {
        headers["Content-Type"] = "application/json";
    }
    return headers;
};

// Listar livros
async function listarLivros() {
    try {
        const response = await fetch(`${API_URL}/livros`, {
            headers: getHeaders()
        });

        if (!response.ok) throw new Error("Erro ao buscar livros");

        const livros = await response.json();
        const lista = document.getElementById("lista-livros");
        
        // Limpa a lista de forma eficiente
        lista.replaceChildren();

        livros.forEach(livro => {
            const li = document.createElement("li");
            li.className = "item-livro"; // Opcional: para seu CSS
            li.textContent = `${livro.titulo} — Status: ${livro.status}`;
            lista.appendChild(li);
        });
    } catch (error) {
        console.error("Falha na operação:", error);
    }
}

// Registrar empréstimo
async function registrarEmprestimo(event) {
    event.preventDefault();

    const usuarioId = document.getElementById("usuarioId").value;
    const livroId = document.getElementById("livroId").value;

    const payload = { 
        usuarioId: parseInt(usuarioId), 
        livroId: parseInt(livroId) 
    };

    try {
        const response = await fetch("http://localhost:8080/emprestimos", {
            method: "POST",
            headers: getHeaders(true),
            body: JSON.stringify(payload)
        });

        if (response.ok) {
            alert("Empréstimo registrado com sucesso!");
            event.target.reset(); // Limpa o formulário
            listarEmprestimos();
            listarLivros(); // Atualiza a lista de livros (caso o status mude)
        } else {
            const errorData = await response.json();
            alert(`Erro: ${errorData.message || "Não foi possível registrar o empréstimo"}`);
        }
    } catch (error) {
        alert("Erro de conexão com o servidor.");
    }
}

// Listar empréstimos
async function listarEmprestimos() {
    try {
        const response = await fetch(`${API_URL}/emprestimos`, {
            headers: getHeaders()
        });

        if (!response.ok) throw new Error("Erro ao buscar empréstimos");

        const emprestimos = await response.json();
        const lista = document.getElementById("lista-emprestimos");

        lista.replaceChildren();

        emprestimos.forEach(emp => {
            const li = document.createElement("li");
            const statusDevolvido = emp.devolvido ? "Sim" : "Não";
            
            li.textContent = `Livro: ${emp.livro.titulo} | Usuário: ${emp.usuario.nome} | Devolvido: ${statusDevolvido}`;
            lista.appendChild(li);
        });
    } catch (error) {
        console.error("Erro ao listar empréstimos:", error);
    }
}