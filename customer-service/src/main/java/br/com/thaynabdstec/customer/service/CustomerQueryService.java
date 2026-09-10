package br.com.thaynabdstec.customer.service;

import br.com.thaynabdstec.customer.domain.Customer;
import br.com.thaynabdstec.customer.dto.CustomerResponse;
import br.com.thaynabdstec.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerQueryService {

    private final CustomerRepository repository;

    public CustomerQueryService(CustomerRepository repository) {
        this.repository = repository;
    }

    public List<CustomerResponse> findAll() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public CustomerResponse findById(UUID id) {
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado."));

        return toResponse(customer);
    }

    private CustomerResponse toResponse(Customer c) {
        return new CustomerResponse(
                c.getId(), c.getName(), c.getEmail(), c.getDocument(),
                c.getCustomerType(), c.getCep(), c.getNumber(), null, null
        );
    }
}
