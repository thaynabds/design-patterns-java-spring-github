package br.com.thaynabdstec.address.dto;

public record AddressResponse(
        String cep,
        String street,
        String neighborhood,
        String city,
        String state
) {}
