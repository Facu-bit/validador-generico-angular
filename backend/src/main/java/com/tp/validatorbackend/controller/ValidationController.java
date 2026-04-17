package com.tp.validatorbackend.controller;

import com.tp.validatorbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/validators")
@CrossOrigin(origins = "http://localhost:4200") // Permite peticiones desde Angular
public class ValidationController {

    @Autowired
    private UserRepository userRepository;

    /**
     * Endpoint genérico de validación de unicidad.
     *
     * Uso: GET /api/validators/check-unique?field=email&value=test@test.com
     *
     * @param field  Campo a validar: "email" o "dni"
     * @param value  Valor que se quiere verificar
     * @return true  si ya existe (está tomado), false si está disponible
     */
    @GetMapping("/check-unique")
    public ResponseEntity<Boolean> checkUnique(
            @RequestParam String field,
            @RequestParam String value
    ) {
        boolean isTaken = false;

        switch (field) {
            case "email":
                isTaken = userRepository.existsByEmail(value);
                break;
            case "dni":
                isTaken = userRepository.existsByDni(value);
                break;
            // Extendible: agregar más casos sin romper nada
            default:
                // Campo no reconocido → devuelve false (no bloquea el formulario)
                break;
        }

        return ResponseEntity.ok(isTaken);
    }
}
