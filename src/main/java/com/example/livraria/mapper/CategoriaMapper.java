package com.example.livraria.mapper;

import com.example.livraria.dto.Categoria.CategoriaRequest;
import com.example.livraria.dto.Categoria.CategoriaResponse;
import com.example.livraria.model.Categoria;
import org.springframework.stereotype.Service;

@Service
public class CategoriaMapper {

    public Categoria toCategoria(CategoriaRequest categoriaDTO
    ) {
        Categoria categoria = new Categoria();

        categoria.setCategoria(categoriaDTO.categoria());
        return categoria;
    }

    public CategoriaResponse toCategoriaDTO(Categoria categoria) {
        return new CategoriaResponse(
                categoria.getId(),
                categoria.getCategoria()
        );
    }
}
