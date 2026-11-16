# Alerta360 — API (Domain Driven Design - Java)

Global Solution 2026.2 — FIAP — CitizenTech
**Território:** Jardim Helena — São Paulo (SP)

## Ficha da CitizenTech

| Campo | Valor |
|---|---|
| Nome da CitizenTech | Alerta360 |
| Cidade e bairro | São Paulo — Jardim Helena |
| Cidadão beneficiado | Moradores, comerciantes e pedestres de Jardim Helena que enfrentam alagamentos recorrentes |
| Problema, em uma frase | Moradores de áreas sujeitas a alagamento não encontram, em um só lugar, informação local e atualizada sobre pontos críticos, ocorrências relatadas e orientações preventivas |
| Como funciona | Plataforma comunitária: cidadão relata ocorrência num ponto crítico, equipe acompanha o status, painel mostra orientações preventivas |
| O que esta parte faz | API RESTful (Spring Boot + Oracle) com as entidades do domínio: Usuário, Ponto Crítico, Ocorrência e Orientação |
| Integrantes | Gustavo Garcia Silva — RM562078; João Marcelo Diniz Vespa — RM564038; João Vitor Reis Silva — RM563115; Nicolas Santana Gará — RM561461; Thiago Luza Alves — RM562219 |

## Arquitetura (DDD simplificado)

```
Controller  →  BO (regras de negócio)  →  DAO (Spring Data JPA)  →  Oracle (FIAP)
                        ↑
                 Exception Handler (@RestControllerAdvice)
```

- **model** — entidades JPA: `Usuario`, `PontoCritico`, `Ocorrencia`, `Orientacao` (+ enums `TipoUsuario`, `CategoriaOcorrencia`, `StatusOcorrencia`).
- **dao** — repositórios Spring Data JPA (`UsuarioRepository`, `PontoCriticoRepository`, `OcorrenciaRepository`, `OrientacaoRepository`).
- **bo** — regras de negócio (ex.: e-mail duplicado, transição de status válida de uma ocorrência: `RECEBIDO → EM_ANALISE → ENCAMINHADO → CONCLUIDO`).
- **controller** — endpoints REST (GET/POST/PUT/DELETE).
- **dto** — contratos de entrada/saída da API, desacoplados das entidades.
- **exception** — exceções de domínio + `GlobalExceptionHandler` (tratamento central de erros).
- **config.DataSeeder** — popula alguns pontos críticos e orientações de Jardim Helena na primeira execução, só para facilitar a demonstração.

## Pré-requisitos

- Java JDK 17
- Maven 3.9+ (ou use a IDE — IntelliJ já traz o Maven embutido)
- Acesso ao banco **Oracle da FIAP** (usuário/senha fornecidos pela instituição) **ou** Docker, para build/deploy

## Como rodar localmente

1. Defina as variáveis de ambiente com as credenciais do Oracle da FIAP (nunca commitar senha no código):

   **Windows (PowerShell)**
   ```powershell
   $env:DB_URL="jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL"
   $env:DB_USER="RMxxxxxx"
   $env:DB_PASSWORD="sua_senha"
   ```

   **Linux/Mac**
   ```bash
   export DB_URL="jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL"
   export DB_USER="RMxxxxxx"
   export DB_PASSWORD="sua_senha"
   ```

2. Rode a aplicação:
   ```bash
   mvn spring-boot:run
   ```
   ou, pela IDE (IntelliJ): abra o projeto como Maven project e execute `Alerta360Application`.

3. A API sobe em `http://localhost:8080`.

## Rodando os testes automatizados

```bash
mvn test
```

Os testes usam um banco **H2 em memória** (configurado em `src/test/resources/application.properties`), então não dependem do Oracle da FIAP para rodar.

## Endpoints principais

| Recurso | Método | Caminho | Descrição |
|---|---|---|---|
| Usuário | GET | `/api/usuarios` | Lista todos os usuários |
| Usuário | GET | `/api/usuarios/{id}` | Busca um usuário |
| Usuário | POST | `/api/usuarios` | Cadastra um usuário |
| Usuário | PUT | `/api/usuarios/{id}` | Atualiza um usuário |
| Usuário | DELETE | `/api/usuarios/{id}` | Remove um usuário |
| Ponto Crítico | GET | `/api/pontos-criticos` | Lista todos os pontos críticos |
| Ponto Crítico | GET | `/api/pontos-criticos/{id}` | Busca um ponto crítico |
| Ponto Crítico | POST | `/api/pontos-criticos` | Cadastra um ponto crítico |
| Ponto Crítico | PUT | `/api/pontos-criticos/{id}` | Atualiza um ponto crítico |
| Ponto Crítico | DELETE | `/api/pontos-criticos/{id}` | Remove um ponto crítico |
| Ocorrência | GET | `/api/ocorrencias` | Lista ocorrências (filtros opcionais `?status=` e `?pontoCriticoId=`) |
| Ocorrência | GET | `/api/ocorrencias/{id}` | Busca uma ocorrência |
| Ocorrência | POST | `/api/ocorrencias` | Registra uma ocorrência |
| Ocorrência | PUT | `/api/ocorrencias/{id}/status` | Atualiza o status de uma ocorrência |
| Ocorrência | DELETE | `/api/ocorrencias/{id}` | Remove uma ocorrência |
| Orientação | GET | `/api/orientacoes` | Lista orientações preventivas |
| Orientação | GET | `/api/orientacoes/{id}` | Busca uma orientação |
| Orientação | POST | `/api/orientacoes` | Cadastra uma orientação |
| Orientação | PUT | `/api/orientacoes/{id}` | Atualiza uma orientação |
| Orientação | DELETE | `/api/orientacoes/{id}` | Remove uma orientação |

### Exemplo — registrar uma ocorrência

```http
POST /api/ocorrencias
Content-Type: application/json

{
  "usuarioId": 1,
  "pontoCriticoId": 1,
  "categoria": "ALAGAMENTO",
  "descricao": "Agua na altura da calcada apos a chuva das 18h",
  "fotoUrl": null
}
```

## Deploy no render.com

O repositório já contém um `Dockerfile` (multi-stage: build com Maven, runtime com JRE 17) e um `render.yaml` opcional.

Passo a passo:

1. Suba este projeto para um repositório no GitHub.
2. Em [render.com](https://render.com), clique em **New + → Web Service**.
3. Conecte o repositório do GitHub. O Render detecta o `Dockerfile` automaticamente (ou use **New + → Blueprint** e aponte para `render.yaml`).
4. Em **Environment Variables**, cadastre:
   - `DB_URL` → `jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL`
   - `DB_USER` → seu RM da FIAP
   - `DB_PASSWORD` → sua senha do Oracle FIAP
5. Clique em **Create Web Service**. O Render builda a imagem e publica a aplicação; a variável `PORT` é injetada automaticamente pelo Render e já está mapeada em `application.properties`.
6. Copie a URL pública gerada (ex.: `https://alerta360-api.onrender.com`) e inclua no PDF de documentação e no README.

**Importante:** commits feitos no GitHub após o prazo de entrega da GS (19/11, 23h55) não são considerados — zera a nota da parte técnica. Finalize o deploy com folga antes do prazo.

## Ferramentas e versões utilizadas

- IntelliJ IDEA (ou outra IDE Java com suporte a Maven/Spring Boot)
- Java JDK 17
- Spring Boot 3.3.4 (Web, Data JPA, Validation)
- Driver Oracle `ojdbc11`
- Banco de dados Oracle (instância da FIAP)
- Deploy: render.com (Docker)
