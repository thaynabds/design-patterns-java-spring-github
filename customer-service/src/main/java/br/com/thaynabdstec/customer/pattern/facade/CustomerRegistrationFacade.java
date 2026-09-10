package br.com.thaynabdstec.customer.pattern.facade;

import br.com.thaynabdstec.customer.client.AddressClient;
import br.com.thaynabdstec.customer.domain.Customer;
import br.com.thaynabdstec.customer.dto.CustomerRequest;
import br.com.thaynabdstec.customer.dto.CustomerResponse;
import br.com.thaynabdstec.customer.pattern.strategy.CustomerValidationStrategyFactory;
import br.com.thaynabdstec.customer.repository.CustomerRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

@Service
public class CustomerRegistrationFacade {

    private final CustomerRepository repository;
    private final AddressClient addressClient;
    private final CustomerValidationStrategyFactory strategyFactory;

    public CustomerRegistrationFacade(
            CustomerRepository repository,
            AddressClient addressClient,
            CustomerValidationStrategyFactory strategyFactory) {
        this.repository = repository;
        this.addressClient = addressClient;
        this.strategyFactory = strategyFactory;
    }

    @CircuitBreaker(name = "addressService", fallbackMethod = "addressFallback")
    public CustomerResponse register(CustomerRequest request) {
        if (repository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }

        if (repository.existsByDocument(request.document())) {
            throw new IllegalArgumentException("Documento já cadastrado.");
        }

        strategyFactory.get(request.customerType()).validate(request);

        AddressClient.AddressResponse address = addressClient.findByCep(request.cep());

        Customer saved = repository.save(new Customer(
                request.name(),
                request.email(),
                request.document(),
                request.customerType(),
                request.cep(),
                request.number()
        ));

        return toResponse(saved, address);
    }

    private CustomerResponse addressFallback(CustomerRequest request, Throwable ignored) {
        throw new IllegalStateException("Serviço de endereço indisponível. Tente novamente.");
    }

    private CustomerResponse toResponse(Customer customer, AddressClient.AddressResponse address) {
        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getDocument(),
                customer.getCustomerType(),
                customer.getCep(),
                customer.getNumber(),
                address.city(),
                address.state()
        );
    }
}
