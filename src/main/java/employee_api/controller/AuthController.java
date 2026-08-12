package employee_api.controller;

import employee_api.dto.ApiResponse;
import employee_api.dto.AuthResponse;
import employee_api.dto.LoginRequest;
import employee_api.dto.RegisterRequest;
import employee_api.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Valid @RequestBody RegisterRequest request) {

        service.register(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        String token = service.login(request);

        AuthResponse authResponse =
                new AuthResponse(token);

        ApiResponse<AuthResponse> response =
                new ApiResponse<>(
                        true,
                        "Login successful",
                        authResponse
                );

        return ResponseEntity.ok(response);
    }
}