# Clube do Album Identity API

API responsavel pela identidade dos usuarios da plataforma Clube do Album.

## Responsabilidade futura

- Cadastro de usuarios.
- Login e autenticacao.
- Perfil do usuario.

## Tecnologias usadas

- Java 17
- Spring Boot
- Maven

## Como rodar localmente

```bash
mvn spring-boot:run
```

Endpoint inicial:

```http
GET /health
```

Status atual: projeto inicial criado apenas com estrutura base. As funcionalidades serão implementadas nas próximas etapas.

## Docker

Crie um arquivo local de ambiente a partir do exemplo:

```bash
cp .env.example .env
```

Build da imagem:

```bash
docker build -t clube-do-album-identity-api .
```

Execucao local:

```bash
docker run --env-file .env -p 8081:8081 clube-do-album-identity-api
```
