package br.com.thaynabdstec.address.service;

import br.com.thaynabdstec.address.dto.AddressResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class AddressService {

    private final RestClient client;

    public AddressService(RestClient.Builder builder) {
        this.client = builder.baseUrl("https://viacep.com.br/ws").build();
    }

    public AddressResponse findByCep(String cep) {
        String normalized = cep.replaceAll("\\D", "");
        if (!normalized.matches("\\d{8}")) {
            throw new IllegalArgumentException("CEP deve conter 8 dígitos.");
        }

        ViaCepResponse response = client.get()
                .uri("/{cep}/json", normalized)
                .retrieve()
                .body(ViaCepResponse.class);

        if (response == null || Boolean.TRUE.equals(response.erro())) {
            throw new IllegalArgumentException("CEP não encontrado.");
        }

        return new AddressResponse(
                response.cep(),
                response.logradouro(),
                response.bairro(),
                response.localidade(),
                response.uf()
        );
    }

    private record ViaCepResponse(
            String cep,
            String logradouro,
            String bairro,
            String localidade,
            String uf,
            Boolean erro
    ) {}
}
