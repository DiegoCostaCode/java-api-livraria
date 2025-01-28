package com.example.livraria.dto.Livro;

import com.example.livraria.model.Categoria;

import java.time.LocalDate;

public record LivroResponse
        (
                Long id,
                String titulo,
                String isbn,
                Categoria categoria,
                String descricao,
                LocalDate dataPublicacao
        ){
}
