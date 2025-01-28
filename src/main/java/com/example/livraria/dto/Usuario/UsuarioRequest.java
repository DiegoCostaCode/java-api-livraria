package com.example.livraria.dto.Usuario;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.time.LocalDate;

public record UsuarioRequest
        (
                @NotNull(message = "Nome é obrigatório")
                @Length(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
                String nome,
                @NotNull(message = "Email é obrigatório")
                @Email(message = "Email inválido")
                String email,
                @NotNull(message = "Data de nascimento é obrigatória")
                @Past(message = "Data de nascimento inválida")
                @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
                LocalDate dataNascimento
        ) {
}
