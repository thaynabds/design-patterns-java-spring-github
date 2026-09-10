# Catálogo de padrões

## Singleton

Demonstração pelo escopo padrão de beans Spring.

O projeto evita singleton manual com estado global.

## Strategy

A interface `CustomerValidationStrategy` permite trocar a regra de validação conforme o tipo de cliente.

## Facade

`CustomerRegistrationFacade` simplifica o caso de uso de cadastro.

## Dependency Injection

As dependências são recebidas por construtor.

## Circuit Breaker

Resilience4j limita o impacto de falhas no serviço de endereços.

## Gateway

Spring Cloud Gateway centraliza o roteamento dos endpoints.
