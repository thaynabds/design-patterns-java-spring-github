package br.com.thaynabdstec.customer.pattern.strategy;

import br.com.thaynabdstec.customer.domain.CustomerType;
import br.com.thaynabdstec.customer.dto.CustomerRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StandardCustomerValidationStrategyTest {

    private final StandardCustomerValidationStrategy strategy =
            new StandardCustomerValidationStrategy();

    @Test
    void shouldAcceptElevenDigitDocument() {
        var request = new CustomerRequest(
                "Thayná", "thayna@example.com", "12345678901",
                CustomerType.INDIVIDUAL, "50030000", "100"
        );

        assertDoesNotThrow(() -> strategy.validate(request));
    }

    @Test
    void shouldRejectInvalidDocument() {
        var request = new CustomerRequest(
                "Thayná", "thayna@example.com", "123",
                CustomerType.INDIVIDUAL, "50030000", "100"
        );

        assertThrows(IllegalArgumentException.class, () -> strategy.validate(request));
    }
}
