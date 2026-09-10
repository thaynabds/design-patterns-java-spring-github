package br.com.thaynabdstec.customer.controller;

import br.com.thaynabdstec.customer.security.JwtService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtService jwtService;
    private final String username;
    private final String password;

    public AuthController(
            JwtService jwtService,
            @Value("${security.dev-user.username}") String username,
            @Value("${security.dev-user.password}") String password) {
        this.jwtService = jwtService;
        this.username = username;
        this.password = password;
    }

    @PostMapping("/login")
    public TokenResponse login(@Valid @RequestBody LoginRequest request) {
        if (!username.equals(request.username()) || !password.equals(request.password())) {
            throw new IllegalArgumentException("Credenciais inválidas.");
        }

        return new TokenResponse(jwtService.generate(username));
    }

    public record LoginRequest(@NotBlank String username, @NotBlank String password) {}
    public record TokenResponse(String token) {}
}
