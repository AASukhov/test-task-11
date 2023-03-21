package task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import task.entity.User;
import task.entity.enums.Role;
import task.repository.UserRepository;

import javax.persistence.Id;

@SpringBootApplication
public class TaskApplication implements CommandLineRunner{

    public static void main(String[] args) {
        SpringApplication.run(TaskApplication.class, args);
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public void run(String... args) throws Exception {
        User admin = new User(1,"Alexey",
                "alexey@gmail.com", encoder.encode("0000"), 120, String.valueOf(Role.ADMIN));
        User user = new User(2,"Sergey",
                "sergey@gmail.com",encoder.encode("1111"),120, String.valueOf(Role.USER));
        userRepository.save(admin);
        userRepository.save(user);
    }
}
