package com.example.livraria.mapper;

import com.example.livraria.dto.Livro.LivroRequest;
import com.example.livraria.dto.Livro.LivroResponse;
import com.example.livraria.model.Livro;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
public class LivroMapper {
    public Livro toLivro(LivroRequest livroDTO
    ) {
        Livro livro = new Livro();
        livro.setTitulo(livroDTO.titulo());
        livro.setDescricao(livroDTO.descricao());
        livro.setIsbn(livroDTO.isbn());
        livro.setDataPublicacao(livroDTO.dataPublicacao());
        livro.setCategoria(livroDTO.categorias());
        return livro;
    }

    public LivroResponse toLivroDTO(Livro livro) {
        return new LivroResponse(
                livro.getId(),
                livro.getTitulo(),
                livro.getIsbn(),
                livro.getCategoria(),
                livro.getDescricao(),
                livro.getDataPublicacao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        );
    }
}
