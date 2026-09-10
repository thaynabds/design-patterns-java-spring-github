package br.com.thaynabdstec.customer.pattern.strategy;

import br.com.thaynabdstec.customer.domain.CustomerType;
import org.springframework.stereotype.Component;

@Component
public class CustomerValidationStrategyFactory {

    private final StandardCustomerValidationStrategy individual;
    private final BusinessCustomerValidationStrategy business;

    public CustomerValidationStrategyFactory(
            StandardCustomerValidationStrategy individual,
            BusinessCustomerValidationStrategy business) {
        this.individual = individual;
        this.business = business;
    }

    public CustomerValidationStrategy get(CustomerType type) {
        return type == CustomerType.BUSINESS ? business : individual;
    }
}
