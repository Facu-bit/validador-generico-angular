package com.tp.validatorbackend;

import com.tp.validatorbackend.entity.User;
import com.tp.validatorbackend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Carga datos de prueba al iniciar la aplicación.
 * Así podés probar de inmediato que la validación funciona.
 */
@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(UserRepository userRepository) {
        return args -> {
            userRepository.save(new User("juan@gmail.com", "12345678"));
            userRepository.save(new User("maria@hotmail.com", "87654321"));
            System.out.println("✅ Datos de prueba cargados.");
            System.out.println("   → email existente: juan@gmail.com");
            System.out.println("   → DNI existente:   12345678");
        };
    }
}
