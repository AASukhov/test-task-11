package task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import task.dto.AuthenticationResponseDTO;
import task.dto.UserAuthenticationDTO;
import task.dto.UserRegistrationDTO;
import task.entity.enums.Role;
import task.entity.User;
import task.entity.enums.Status;
import task.repository.UserRepository;
import task.security.TokenCreator;

@Service
public class AuthService {

    private AuthenticationManager manager;
    private TokenCreator creator;
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    public AuthService(AuthenticationManager manager,
                       TokenCreator creator,
                       UserRepository userRepository) {
        this.manager = manager;
        this.creator = creator;
        this.userRepository = userRepository;
    }

    public ResponseEntity<AuthenticationResponseDTO> login(@RequestBody UserAuthenticationDTO request) {
        Authentication authentication = manager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getName(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = creator.createToken(authentication);
        AuthenticationResponseDTO response = new AuthenticationResponseDTO(token);
        if (response.getToken() == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<String> register(@RequestBody UserRegistrationDTO request) {
        if (userRepository.existsByName(request.getName())) {
            return new ResponseEntity<>("The name has been taken", HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        int id =  (int) Math.round(100 * Math.random());
        user.setId(id);
        user.setName(request.getName());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setBalance(0);
        user.setRole(String.valueOf(Role.USER));
        userRepository.save(user);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }
}
