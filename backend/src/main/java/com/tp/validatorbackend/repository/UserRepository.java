package com.tp.validatorbackend.repository;

import com.tp.validatorbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Spring Data genera el SQL automáticamente — solo devuelve boolean (eficiente)
    boolean existsByEmail(String email);
    boolean existsByDni(String dni);
}
