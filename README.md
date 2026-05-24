Sistema de Biblioteca

Projeto de gerenciamento de biblioteca com cadastro de usuários, livros e controle de empréstimos.

Tecnologias utilizadas
**Java 17** + **Spring Boot**
**H2 Database** (ou outro banco configurado)
**HTML5 / CSS3 / JavaScript**
**Fetch API** para comunicação com o backend
=================================================
Funcionalidades
Cadastro, listagem e exclusão de **usuários**
Cadastro, listagem e exclusão de **livros**
Registro e devolução de **empréstimos**
Visualização de:
  - Todos os livros
  - Apenas livros emprestados
  - Histórico dos empréstimos
===================================================
Estrutura do projeto
Projeto-BIBLIOTECA/
├── src/main/java/com/example/ReservaBiblioteca/
│    ├── controller/   # Controllers REST
│    ├── service/      # Regras de negócio
│    ├── model/        # Entidades (Usuario, Livro, Emprestimo)
│    └── repository/   # Interfaces JPA
├── src/main/resources/
│    ├── static/       # HTML, CSS, JS
│    └── application.properties
└── README.md
=====================================================
Como executar
1. Clone o repositório:
   ```bash
   git clone https://github.com/Carneiro2025/Projeto-BIBLIOTECA.git
2.Entre na pasta do projeto:
cd\Projeto-BIBLIOTECA
3. Compile e rode o backend:
./mvnw spring-boot:run
4.Acesse no navegador:

http://localhost:8080/user.html

http://localhost:8080/liv.html

http://localhost:8080/index.html

http://localhost:8080/dev.html

==============================================
Endpoints principais
Usuários
GET /api/usuarios → listar usuários
POST /api/usuarios → cadastrar usuário
DELETE /api/usuarios/{id} → excluir usuário

Livros
GET /api/livros → listar livros
POST /api/livros → cadastrar livro
DELETE /api/livros/{id} → excluir livro

Empréstimos
GET /api/emprestimos → listar empréstimos
POST /api/emprestimos → registrar empréstimo
PUT /api/emprestimos/{id}/devolucao → devolver empréstimo













