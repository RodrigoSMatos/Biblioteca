# 📚 Sistema de Gerenciamento de Biblioteca – API Spring Boot

Microserviço REST para gerenciamento de uma biblioteca, desenvolvido em **Java + Spring Boot**, com foco em boas práticas de arquitetura, testes automatizados e documentação.

Permite o cadastro e a consulta de **livros** e **categorias**, com validações, relacionamento JPA (`Livro` → `Categoria`) e tratamento centralizado de erros.

---

## ✅ Sumário

1. [Visão Geral](#-visão-geral)
2. [Tecnologias Utilizadas](#-tecnologias-utilizadas)
3. [Arquitetura do Projeto](#-arquitetura-do-projeto)
4. [Pré-requisitos](#-pré-requisitos)
5. [Como Rodar Localmente](#-como-rodar-localmente)
    - [Perfil `dev` (H2 em memória)](#perfil-dev-h2-em-memória)
    - [Perfil `prod` (MySQLLocal-ou-Cloud)](#perfil-prod-mysql-local-ou-cloud)
6. [Configuração de Banco de Dados](#-configuração-de-banco-de-dados)
7. [Comandos Maven Úteis](#-comandos-maven-úteis)
8. [Documentação da API (Swagger)](#-documentação-da-api-swagger)
9. [Endpoints Principais](#-endpoints-principais)
10. [Exemplos de uso com cURL](#-exemplos-de-uso-com-curl)
11. [Testes Automatizados e Cobertura](#-testes-automatizados-e-cobertura)
12. [Deploy em Produção](#-deploy-em-produção)
13. [Divisão de Tarefas do Grupo](#-divisão-de-tarefas-do-grupo)
14. [Estrutura de Pastas (visão geral)](#-estrutura-de-pastas-visão-geral)

---

## 🔎 Visão Geral

Este projeto implementa um **microserviço de Biblioteca** responsável por:

- Cadastrar e gerenciar **categorias** de livros
- Cadastrar, listar, atualizar e excluir **livros**
- Relacionar livros a uma categoria (`ManyToOne`)
- Expor uma API REST documentada via **Swagger/OpenAPI**
- Validar os dados de entrada com **Bean Validation**
- Tratar erros de forma centralizada com um **GlobalExceptionHandler**

---

## 🛠 Tecnologias Utilizadas

- **Java 21** (ou compatível)
- **Spring Boot 3.x**
    - Spring Web
    - Spring Data JPA
    - Validation
- **Banco de Dados**
    - `H2` (ambiente de desenvolvimento – profile `dev`)
    - `MySQL` / `PostgreSQL` (produção – profile `prod`)
- **Maven** (build/gerenciamento de dependências)
- **Lombok** (redução de boilerplate)
- **Springdoc OpenAPI** (Swagger UI)
- **JUnit 5** + **Mockito** (testes unitários)
- **JaCoCo** (relatório de cobertura de testes)

---

## 🧱 Arquitetura do Projeto

Arquitetura em camadas, separando responsabilidades:

- `controller` → recebe requisições HTTP, valida DTOs, retorna respostas REST
- `service` → contém a regra de negócio da aplicação
- `repository` → acesso a dados via Spring Data JPA
- `model` → entidades JPA (`Livro`, `Categoria`)
- `dto` → objetos de transferência para entrada/saída da API
- `mapper` → conversão entre Model ↔ DTO
- `exception` → classes de erro e tratador global (`GlobalExceptionHandler`)

---

## 📦 Pré-requisitos

Para rodar o projeto localmente, você precisa de:

- **Java JDK 17+** (recomendado 21)
- **Maven 3.8+** (ou usar o `mvnw` do próprio projeto)
- **MySQL** ou **PostgreSQL** (para perfil `prod`, opcional se for usar só H2)
- IDE recomendada: **IntelliJ IDEA** ou **Eclipse/VS Code** com suporte a Maven

---

## ▶ Como Rodar Localmente

### Perfil `dev` (H2 em memória)

Esse é o perfil padrão para desenvolvimento. O banco é em memória, criado do zero a cada execução.

**1. Clonar o repositório**

```bash
git clone https://github.com/SEU-USUARIO/SEU-REPOSITORIO.git
cd SEU-REPOSITORIO
