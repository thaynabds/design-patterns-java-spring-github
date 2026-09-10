# Design Patterns com Java e Spring

Projeto autoral desenvolvido para o desafio **Design Patterns com Java: Dos Clássicos (GoF) ao Spring Framework**, do Bootcamp DIO Java AI Copilot.

A proposta é demonstrar padrões de projeto em uma API REST organizada em microsserviços, combinando Java, Spring Boot, Spring Cloud Gateway, persistência, validação, testes, Docker e documentação OpenAPI.

## Índice

1. [Sobre o projeto](#sobre-o-projeto)
2. [Arquitetura](#arquitetura)
3. [Padrões implementados](#padrões-implementados)
4. [Tecnologias](#tecnologias)
5. [Estrutura do repositório](#estrutura-do-repositório)
6. [Requisitos](#requisitos)
7. [Configuração](#configuração)
8. [Execução com Docker](#execução-com-docker)
9. [Execução local](#execução-local)
10. [Endpoints](#endpoints)
11. [Testes](#testes)
12. [Segurança](#segurança)
13. [Referências](#referências)
14. [Autora](#autora)

## Sobre o projeto

O sistema representa uma pequena plataforma de cadastro de clientes com consulta de endereço.

O fluxo principal é:

1. O cliente acessa a API pelo API Gateway.
2. O microsserviço de clientes recebe a solicitação.
3. A camada de serviço aplica as regras de negócio.
4. A Facade coordena o cadastro e a consulta de endereço.
5. O microsserviço de endereços consulta o serviço externo ViaCEP.
6. O Strategy seleciona a política de validação do cadastro.
7. O Spring gerencia os componentes como beans, explorando o conceito de singleton por container.
8. O Circuit Breaker protege a comunicação entre microsserviços.

O projeto não utiliza uma aplicação monolítica única. O gateway, o serviço de clientes e o serviço de endereços possuem responsabilidades separadas.

## Arquitetura

```text
                    +----------------------+
                    |      API Gateway     |
                    |       :8080          |
                    +----------+-----------+
                               |
                 +-------------+-------------+
                 |                           |
                 v                           v
       +-------------------+       +-------------------+
       | Customer Service  |       | Address Service   |
       |       :8081       |------>|       :8082       |
       +---------+---------+       +---------+---------+
                 |                           |
                 v                           v
       +-------------------+       +-------------------+
       | PostgreSQL        |       | ViaCEP            |
       | customer_db       |       | API externa       |
       +-------------------+       +-------------------+
```

Cada microsserviço possui suas próprias responsabilidades e não acessa o banco do outro serviço.

## Padrões implementados

### Singleton

O Spring utiliza escopo singleton por padrão para beans gerenciados pelo container. O projeto usa componentes stateless gerenciados pelo Spring, sem criar singleton manual com estado global.

A documentação oficial destaca que o singleton do Spring é diferente do Singleton GoF: no Spring, o escopo é por container e por bean.

### Strategy

A validação do cadastro é definida por uma interface de estratégia. Cada estratégia representa uma regra de validação específica.

Implementação:

```text
CustomerValidationStrategy
    |
    +-- StandardCustomerValidationStrategy
    |
    +-- BusinessCustomerValidationStrategy
```

A seleção ocorre por uma factory baseada no tipo de cliente.

### Facade

`CustomerRegistrationFacade` fornece uma operação de alto nível para registrar um cliente. Ela coordena:

1. validação;
2. consulta de endereço;
3. persistência;
4. montagem da resposta.

O Controller não conhece os detalhes internos desse fluxo.

### Dependency Injection

As dependências são recebidas por construtor. Isso reduz acoplamento e facilita testes unitários.

### Circuit Breaker

A comunicação do Customer Service com o Address Service utiliza Resilience4j. Em caso de indisponibilidade, o fluxo retorna uma resposta controlada em vez de propagar uma falha em cascata.

### API Gateway

O Spring Cloud Gateway centraliza a entrada da aplicação e encaminha as requisições para os microsserviços.

## Tecnologias

| Tecnologia | Uso |
|---|---|
| Java 17 | Linguagem |
| Spring Boot 4.1.1 | Aplicações |
| Spring Cloud 2025.1.3 | Recursos distribuídos |
| Spring Cloud Gateway | API Gateway |
| Spring Data JPA | Persistência |
| PostgreSQL | Banco do Customer Service |
| WebClient | Comunicação HTTP |
| Resilience4j | Circuit Breaker |
| Spring Security | Segurança |
| JJWT | Emissão e validação de JWT |
| OpenAPI | Documentação REST |
| JUnit 6 | Testes |
| Mockito | Mocks |
| Docker Compose | Ambiente local |

## Estrutura do repositório

```text
design-patterns-java-spring/
├── api-gateway/
├── customer-service/
├── address-service/
├── docker/
├── docs/
├── scripts/
├── .github/
├── .gitignore
├── docker-compose.yml
└── README.md
```

## Requisitos

* JDK 17 ou superior.
* Maven 3.6.3 ou superior.
* Docker e Docker Compose.
* Git.

## Configuração

As credenciais são definidas por variáveis de ambiente.

Exemplo:

```bash
export DB_USERNAME=app
export DB_PASSWORD=app
export JWT_SECRET=change-me-in-production
```

Nunca versionar arquivos `.env` com credenciais reais.

## Execução com Docker

```bash
docker compose up --build
```

Serviços:

```text
Gateway:           http://localhost:8080
Customer Service:  http://localhost:8081
Address Service:   http://localhost:8082
```

Documentação OpenAPI:

```text
http://localhost:8081/swagger-ui.html
http://localhost:8082/swagger-ui.html
```

## Execução local

Em cada serviço:

```bash
./mvnw spring-boot:run
```

No Linux ou macOS, se necessário:

```bash
chmod +x mvnw
```

O projeto também pode ser executado com Maven instalado:

```bash
mvn spring-boot:run
```

## Endpoints

### Autenticação

```http
POST /api/auth/login
Content-Type: application/json
```

Corpo:

```json
{
  "username": "admin",
  "password": "admin123"
}
```

A resposta contém um JWT para uso nos endpoints protegidos.

### Clientes

```http
POST /api/customers
GET /api/customers/{id}
GET /api/customers
```

Exemplo de cadastro:

```json
{
  "name": "Thayná Batista da Silva",
  "email": "thayna@example.com",
  "document": "12345678900",
  "customerType": "INDIVIDUAL",
  "cep": "50030000",
  "number": "100"
}
```

### Endereços

```http
GET /api/addresses/{cep}
```

O Address Service consulta o ViaCEP e normaliza a resposta para o contrato interno da aplicação.

## Testes

Executar todos os testes:

```bash
mvn test
```

Executar um microsserviço:

```bash
cd customer-service
mvn test
```

O projeto possui testes unitários para as estratégias, facade e regras principais.

## Segurança

O projeto demonstra:

* autenticação baseada em JWT;
* segredo configurável por variável de ambiente;
* validação de entrada;
* separação de responsabilidades;
* ausência de credenciais hardcoded;
* `.gitignore` configurado;
* endpoints administrativos protegidos.

Para produção, recomenda-se utilizar um provedor OAuth2/OIDC dedicado, gerenciamento de segredos e TLS obrigatório.

## Decisões de engenharia

O projeto evita um singleton GoF manual para serviços Spring porque o próprio container já fornece escopo singleton. Isso permite demonstrar o conceito sem introduzir estado global desnecessário.

A arquitetura de microsserviços foi adotada como requisito de engenharia do projeto, embora uma aplicação didática desse tamanho também pudesse ser implementada como monólito modular em um cenário real. A escolha aqui é deliberada para demonstrar comunicação entre serviços, gateway e resiliência.

## Referências

* DIO, laboratório de padrões de projeto com Java Puro:
  https://github.com/digitalinnovationone/lab-padroes-projeto-java
* DIO, laboratório de padrões de projeto com Spring:
  https://github.com/digitalinnovationone/lab-padroes-projeto-spring
* Spring Boot:
  https://spring.io/projects/spring-boot
* Spring Framework, Dependency Injection:
  https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-collaborators.html
* Spring Framework, Bean Scopes:
  https://docs.spring.io/spring-framework/reference/core/beans/factory-scopes.html
* Spring Cloud:
  https://spring.io/projects/spring-cloud
* Spring Cloud Gateway:
  https://docs.spring.io/spring-cloud-gateway/reference/
* ViaCEP:
  https://viacep.com.br/

## Autora

### Thayná Batista da Silva

Estudante de **Análise e Desenvolvimento de Sistemas**, Faculdade Senac Recife, Recife, Pernambuco, Brasil.  
Período informado: **2025 a 2027**.

Email: thaynabdstec@gmail.com  
Telefone: +55 (81) 97912-6121

Projeto desenvolvido para o **Bootcamp DIO Java AI Copilot**, em 10 de setembro de 2026.

**Copyright © 2026, ThaynaBDSTec. Todos os direitos reservados.**
