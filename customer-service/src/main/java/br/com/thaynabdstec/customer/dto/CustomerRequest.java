package br.com.thaynabdstec.customer.dto;

import br.com.thaynabdstec.customer.domain.CustomerType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CustomerRequest(
        @NotBlank String name,
        @NotBlank @Email String email,
        @NotBlank String document,
        @NotNull CustomerType customerType,
        @NotBlank String cep,
        @NotBlank String number
) {}
