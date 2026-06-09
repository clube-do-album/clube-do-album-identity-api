# Clube do Album Identity API

API responsavel pela identidade dos usuarios da plataforma Clube do Album.

## Responsabilidade

- Cadastrar usuarios.
- Realizar login simples.
- Listar usuarios.
- Buscar usuarios por nome ou e-mail.
- Buscar usuario por id.
- Salvar senha com hash BCrypt.
- Emitir JWT para uso futuro pelo frontend/gateway.

Protecao de endpoints com JWT fica para uma etapa futura.

## Tecnologias usadas

- Java 17
- Spring Boot
- Spring Data JPA
- Bean Validation
- PostgreSQL
- Maven

## Variaveis de ambiente

Crie um arquivo local a partir do exemplo:

```bash
cp .env.example .env
```

Variaveis esperadas:

```env
SERVER_PORT=8081

DATABASE_URL=jdbc:postgresql://127.0.0.1:15432/clube_do_album_identity
DATABASE_USERNAME=clube
DATABASE_PASSWORD=clube

JWT_SECRET=clube-do-album-local-development-secret-key-change-me
JWT_EXPIRATION_SECONDS=3600
```

## Banco de dados

Esta API usa o database exclusivo:

```text
clube_do_album_identity
```

Com o PostgreSQL da infraestrutura local rodando, crie o banco caso ainda nao exista:

```bash
docker exec clube-do-album-postgres psql -U clube -d postgres -c "CREATE DATABASE clube_do_album_identity;"
```

As tabelas sao criadas pelo Hibernate nesta etapa com:

```yaml
spring.jpa.hibernate.ddl-auto: update
```

## Como rodar localmente

```bash
mvn spring-boot:run
```

Health check:

```http
GET /health
```

## Endpoints

### Criar usuario

```http
POST /users
```

Body:

```json
{
  "name": "Maria Silva",
  "email": "maria@example.com",
  "password": "123456"
}
```

Resposta:

```json
{
  "id": "uuid-do-usuario",
  "name": "Maria Silva",
  "email": "maria@example.com",
  "createdAt": "2026-06-01T18:00:00Z",
  "updatedAt": "2026-06-01T18:00:00Z"
}
```

### Listar usuarios

```http
GET /users
```

### Buscar usuarios por nome ou e-mail

```http
GET /users?query=maria
```

### Buscar usuario por id

```http
GET /users/{id}
```

### Login

```http
POST /auth/login
```

Body:

```json
{
  "email": "maria@example.com",
  "password": "123456"
}
```

Resposta:

```json
{
  "accessToken": "jwt",
  "tokenType": "Bearer",
  "expiresIn": 3600,
  "user": {
    "id": "uuid-do-usuario",
    "name": "Maria Silva",
    "email": "maria@example.com",
    "createdAt": "2026-06-01T18:00:00Z",
    "updatedAt": "2026-06-01T18:00:00Z"
  }
}
```

## Regras

- `email` deve ser unico.
- `email` e salvo em minusculo.
- `password` deve ter entre 6 e 72 caracteres.
- A senha nunca e retornada pela API.
- A senha e salva no banco como `password_hash`.

## Docker

Build da imagem:

```bash
docker build -t clube-do-album-identity-api .
```

Execucao em container na network local:

```bash
docker run -d --name clube-do-album-identity-api \
  --network clube-do-album-network \
  -e SERVER_PORT=8081 \
  -e DATABASE_URL=jdbc:postgresql://clube-do-album-postgres:5432/clube_do_album_identity \
  -e DATABASE_USERNAME=clube \
  -e DATABASE_PASSWORD=clube \
  -e JWT_SECRET=clube-do-album-local-development-secret-key-change-me \
  -e JWT_EXPIRATION_SECONDS=3600 \
  -p 8081:8081 \
  clube-do-album-identity-api
```

## Status atual

API criada com cadastro, listagem, busca de usuarios por nome/e-mail, busca por id e login com JWT. Protecao dos endpoints com JWT sera implementada em etapa futura.
