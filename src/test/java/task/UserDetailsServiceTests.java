package task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import task.repository.UserRepository;
import task.service.CustomUserDetailsService;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserDetailsServiceTests {

    @InjectMocks
    private CustomUserDetailsService service;

    @Mock
    private UserRepository userRepository;

    public static final String NAME = "alex";
    public static final String PASSWORD = "0000";
    public static final String EMAIL = "alex@mail.com";
    public static final task.entity.User USER_1 = new task.entity.User(NAME,PASSWORD,EMAIL);

    public static final UserDetails USER_DETAILS = User.builder()
            .username(NAME)
            .password(PASSWORD)
            .authorities(new ArrayList<>())
            .build();

    @BeforeEach
    void setUp() {
        Mockito.when(userRepository.findUserByName(NAME)).thenReturn(Optional.of(USER_1));
    }

    @Test
    void loadUserByUsername() {
        assertEquals(USER_DETAILS, service.loadUserByUsername(NAME));
    }
}
