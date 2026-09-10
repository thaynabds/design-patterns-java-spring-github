package br.com.thaynabdstec.customer.pattern.strategy;

import br.com.thaynabdstec.customer.dto.CustomerRequest;

public interface CustomerValidationStrategy {
    void validate(CustomerRequest request);
}
