package br.com.thaynabdstec.customer.dto;

import br.com.thaynabdstec.customer.domain.CustomerType;

import java.util.UUID;

public record CustomerResponse(
        UUID id,
        String name,
        String email,
        String document,
        CustomerType customerType,
        String cep,
        String number,
        String city,
        String state
) {}
