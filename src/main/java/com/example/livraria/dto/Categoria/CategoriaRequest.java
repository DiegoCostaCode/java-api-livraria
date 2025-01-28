package com.example.livraria.dto.Categoria;

import org.hibernate.validator.constraints.Length;

public record CategoriaRequest
        (
                @Length(min = 3, max = 100, message = "A categoria deve ter entre 3 e 100 caracteres")
                String categoria
        ) {
}
