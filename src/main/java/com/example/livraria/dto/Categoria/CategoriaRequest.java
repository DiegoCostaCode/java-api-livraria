package com.example.livraria.dto.Categoria;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record CategoriaRequest
        (
                @NotNull(message = "A categoria é obrigatória")
                @Length(min = 3, max = 100, message = "A categorias deve ter entre 3 e 100 caracteres")
                String categoria
        ) {
}
