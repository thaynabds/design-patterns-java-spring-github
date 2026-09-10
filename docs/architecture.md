# Arquitetura

## Serviços

### API Gateway

Responsável por receber as requisições externas e encaminhá-las aos serviços internos.

### Customer Service

Responsável pelo domínio de clientes, autenticação e persistência dos clientes.

Camadas:

```text
Controller
   |
Facade / Service
   |
Repository
   |
PostgreSQL
```

### Address Service

Responsável pela consulta e normalização de endereços.

```text
Controller
   |
Service
   |
ViaCEP
```

## Comunicação

O Customer Service não acessa o banco do Address Service.

A integração ocorre por HTTP, utilizando o contrato exposto pelo Address Service.

O Circuit Breaker protege o Customer Service contra indisponibilidade do Address Service.
