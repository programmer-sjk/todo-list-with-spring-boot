package example.todolist.configure;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {
    public static String encode(String seed) {
        return new BCryptPasswordEncoder().encode(seed);
    }

    public static boolean matches(String seed, String password) {
        return new BCryptPasswordEncoder().matches(seed, password);
    }
}
