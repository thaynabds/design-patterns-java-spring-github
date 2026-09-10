package br.com.thaynabdstec.customer.domain;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String document;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CustomerType customerType;

    @Column(nullable = false)
    private String cep;

    @Column(nullable = false)
    private String number;

    protected Customer() {
    }

    public Customer(String name, String email, String document, CustomerType customerType, String cep, String number) {
        this.name = name;
        this.email = email;
        this.document = document;
        this.customerType = customerType;
        this.cep = cep;
        this.number = number;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getDocument() { return document; }
    public CustomerType getCustomerType() { return customerType; }
    public String getCep() { return cep; }
    public String getNumber() { return number; }
}
