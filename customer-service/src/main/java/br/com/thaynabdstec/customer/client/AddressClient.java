package br.com.thaynabdstec.customer.client;

import br.com.thaynabdstec.customer.dto.AddressResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class AddressClient {

    private final RestClient client;

    public AddressClient(RestClient.Builder builder) {
        this.client = builder.baseUrl("${ADDRESS_SERVICE_URL:http://localhost:8082}").build();
    }

    public AddressResponse findByCep(String cep) {
        return client.get()
                .uri("/api/addresses/{cep}", cep)
                .retrieve()
                .body(AddressResponse.class);
    }

    public record AddressResponse(
            String cep,
            String street,
            String neighborhood,
            String city,
            String state
    ) {}
}
