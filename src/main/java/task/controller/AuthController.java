package task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import task.dto.AuthenticationResponseDTO;
import task.dto.UserAuthenticationDTO;
import task.dto.UserRegistrationDTO;
import task.service.AuthService;

@RestController
@RequestMapping()
public class AuthController {

    private AuthService service;

    @Autowired
    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> login (@RequestBody UserAuthenticationDTO request) {
        return service.login(request);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register (@RequestBody UserRegistrationDTO request) {
        return service.register(request);
    }
}
