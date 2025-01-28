package com.example.livraria.dto.Livro;

import com.example.livraria.model.Categorias;

import java.time.LocalDate;

public record LivroResponse
        (
                Long id,
                String titulo,
                String isbn,
                Categorias categorias,
                String descricao,
                String dataPublicacao
        ){
}
