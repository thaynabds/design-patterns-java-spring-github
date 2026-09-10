package br.com.thaynabdstec.address.controller;

import br.com.thaynabdstec.address.service.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService service;

    public AddressController(AddressService service) {
        this.service = service;
    }

    @GetMapping("/{cep}")
    public ResponseEntity<?> find(@PathVariable String cep) {
        return ResponseEntity.ok(service.findByCep(cep));
    }
}
