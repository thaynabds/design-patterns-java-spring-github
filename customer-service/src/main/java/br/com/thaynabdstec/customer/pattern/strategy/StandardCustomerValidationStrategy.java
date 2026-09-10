package br.com.thaynabdstec.customer.pattern.strategy;

import br.com.thaynabdstec.customer.dto.CustomerRequest;
import org.springframework.stereotype.Component;

@Component
public class StandardCustomerValidationStrategy implements CustomerValidationStrategy {

    @Override
    public void validate(CustomerRequest request) {
        if (request.document().replaceAll("\\D", "").length() != 11) {
            throw new IllegalArgumentException("Documento de pessoa física inválido.");
        }
    }
}
