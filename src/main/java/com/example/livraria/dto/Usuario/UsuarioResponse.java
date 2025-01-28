package com.example.livraria.dto.Usuario;

import com.example.livraria.model.Livro;

import java.util.Set;

public record UsuarioResponse
        (
                Long id,
                String nome,
                String email,
                Set<Livro> livrosFavoritos,
                String since
        ) {
}
