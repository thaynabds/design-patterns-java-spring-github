package br.com.thaynabdstec.customer.controller;

import br.com.thaynabdstec.customer.dto.CustomerRequest;
import br.com.thaynabdstec.customer.dto.CustomerResponse;
import br.com.thaynabdstec.customer.pattern.facade.CustomerRegistrationFacade;
import br.com.thaynabdstec.customer.service.CustomerQueryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerRegistrationFacade registrationFacade;
    private final CustomerQueryService queryService;

    public CustomerController(
            CustomerRegistrationFacade registrationFacade,
            CustomerQueryService queryService) {
        this.registrationFacade = registrationFacade;
        this.queryService = queryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse create(@Valid @RequestBody CustomerRequest request) {
        return registrationFacade.register(request);
    }

    @GetMapping
    public List<CustomerResponse> findAll() {
        return queryService.findAll();
    }

    @GetMapping("/{id}")
    public CustomerResponse findById(@PathVariable UUID id) {
        return queryService.findById(id);
    }
}
