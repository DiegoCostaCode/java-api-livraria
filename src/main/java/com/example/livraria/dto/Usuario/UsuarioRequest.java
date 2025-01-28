package com.example.livraria.dto.Usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record UsuarioRequest
        (
                @Length(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
                String nome,
                @Email(message = "Email inválido")
                String email
        ) {
}
