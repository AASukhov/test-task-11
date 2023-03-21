package task.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import task.entity.User;
import task.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        List<GrantedAuthority> authorities = new ArrayList<>();
        User user = userRepository.findUserByName(name).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                authorities
        );
    }
}
