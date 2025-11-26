
# 📚 Sistema de Gerenciamento de Biblioteca – API Spring Boot

API REST completa para gerenciamento de **livros** e **categorias**, desenvolvida com **Java 21** e **Spring Boot 3.3.2**, utilizando arquitetura em camadas, validações, documentação Swagger, testes unitários, cobertura via JaCoCo e profiles DEV/PROD.

Este projeto atende aos requisitos acadêmicos de:
- API REST com CRUD completo
- Entidade com mais de 4 atributos
- Relacionamento JPA
- DTOs e Mapper
- Tratamento global de exceções
- Documentação com Swagger
- Testes unitários (Service + Controller)
- Cobertura mínima de 90% nas camadas avaliadas
- Profiles de execução (H2 / MySQL)
- README completo e padronizado
- Estrutura profissional

---

## 🧾 Repositório Oficial

🔗 **GitHub:** https://github.com/RodrigoSMatos/Biblioteca.git

---

## 📘 Descrição Geral

Este microserviço gerencia dois recursos centrais:

### ✔ Categorias  
- Cadastro  
- Listagem  
- Atualização  
- Exclusão  

### ✔ Livros  
- Cadastro  
- Listagem  
- Atualização  
- Exclusão  
- Relação **ManyToOne** com Categoria

O sistema inclui:
- Validações com Bean Validation  
- Tratamento de erros padronizado com `GlobalExceptionHandler`  
- DTOs de entrada e saída  
- Conversão Model ↔ DTO com `Mapper`  
- Swagger UI para documentação  
- Testes automatizados JUnit + Mockito  
- Cobertura JaCoCo acima de 90% para camadas avaliadas  

---

## 🛠 Tecnologias e Versões Utilizadas

| Tecnologia | Versão |
|-----------|--------|
| Java | **21** |
| Spring Boot | **3.3.2** |
| Spring Web | 3.3.2 |
| Spring Data JPA | 3.3.2 |
| Validation | 3.3.2 |
| Lombok | 1.18.x |
| H2 Database | 2.2.x |
| MySQL Driver | 8.x |
| PostgreSQL Driver | 42.x |
| Springdoc OpenAPI | **2.5.0** |
| JUnit 5 | 5.x |
| Mockito | 5.x |
| JaCoCo | 0.8.11 |
| Maven | 3.8+ |

---

# 🖥️ Como Rodar o Projeto 

## 📌 1. Pré-requisitos

Precisa ter instalado:

- Java **17+** (recomendado Java 21)
- Maven 3.8+
- IntelliJ IDEA, Eclipse ou VSCode

Nenhum banco externo é necessário para o perfil **DEV** (H2).

---

## 📌 2. Como rodar (H2 – perfil DEV)

### ✔ O projeto já vem configurado no perfil DEV  
Arquivo: `src/main/resources/application.properties`

```

spring.profiles.active=dev

````

### ✔ Executar

**Via IntelliJ:**
1. Abrir `BibliotecaApplication.java`
2. Clicar em ▶ **Run**

**Via terminal:**
```bash
mvn spring-boot:run
````

### ✔ Acessar Swagger

```
http://localhost:8080/swagger-ui.html
```

### ✔ Acessar banco H2

```
http://localhost:8080/h2-console
```

**Configuração H2:**

| Campo    | Valor                       |
| -------- | --------------------------- |
| JDBC URL | `jdbc:h2:mem:biblioteca_db` |
| User     | `sa`                        |
| Password | *(vazio)*                   |

---

## 📌 3. Como rodar (MySQL/PostgreSQL – perfil PROD)

### Ativar perfil:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

### Arquivo: `application-prod.properties`

```properties
spring.datasource.url=${SPRING_DATASOURCE_URL}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
```

---

# ⚙️ Configurações Importantes

## ✔ `application-dev.properties` (H2)

```properties
spring.datasource.url=jdbc:h2:mem:biblioteca_db
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true
```

## ✔ `application-prod.properties` (MySQL/PostgreSQL)

```properties
spring.datasource.url=${SPRING_DATASOURCE_URL}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.format_sql=true
```

---

# 📂 Endpoints da API

## Categorias

| Método | Rota               | Descrição         |
| ------ | ------------------ | ----------------- |
| POST   | `/categorias`      | Criar categoria   |
| GET    | `/categorias`      | Listar categorias |
| GET    | `/categorias/{id}` | Buscar por ID     |
| PUT    | `/categorias/{id}` | Atualizar         |
| DELETE | `/categorias/{id}` | Remover           |

## Livros

| Método | Rota           | Descrição     |
| ------ | -------------- | ------------- |
| POST   | `/livros`      | Criar livro   |
| GET    | `/livros`      | Listar livros |
| GET    | `/livros/{id}` | Buscar por ID |
| PUT    | `/livros/{id}` | Atualizar     |
| DELETE | `/livros/{id}` | Remover       |

---

# 📌 Exemplos de uso com cURL

### Criar Categoria

```bash
curl -X POST http://localhost:8080/categorias \
-H "Content-Type: application/json" \
-d '{"nome":"Fantasia"}'
```

### Criar Livro

```bash
curl -X POST http://localhost:8080/livros \
-H "Content-Type: application/json" \
-d '{
  "titulo":"O Hobbit",
  "autor":"J. R. R. Tolkien",
  "isbn":"1234567890",
  "anoPublicacao":1937,
  "quantidadeExemplares":3,
  "categoriaId":1
}'
```

---

# 🧪 Testes Unitários e Cobertura JaCoCo

O projeto contém testes para:

* Controllers
* Services
* Exception Handler
* DTOs / Mappers
* Models
* Classe principal

### ✔ Relatório JaCoCo

Localizado em:

```
target/site/jacoco/index.html
```

### ✔ Cobertura alcançada:

**≈ 90% GLOBAL (camadas avaliadas)**

Atende ao requisito de cobertura mínima.

---

# 🏭 Deploy em Produção (opcional)

Pode ser realizado com:

* Render
* Railway
* Heroku (via Docker)

### Variáveis necessárias:

```
SPRING_PROFILES_ACTIVE=prod
SPRING_DATASOURCE_URL=
SPRING_DATASOURCE_USERNAME=
SPRING_DATASOURCE_PASSWORD=
```

Link do repositório para deploy:

🔗 [https://github.com/RodrigoSMatos/Biblioteca.git](https://github.com/RodrigoSMatos/Biblioteca.git)


