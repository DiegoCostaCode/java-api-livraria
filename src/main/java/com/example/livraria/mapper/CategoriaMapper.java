package com.example.livraria.mapper;

import com.example.livraria.dto.Categoria.CategoriaRequest;
import com.example.livraria.dto.Categoria.CategoriaResponse;
import com.example.livraria.model.Categorias;
import org.springframework.stereotype.Service;

@Service
public class CategoriaMapper {

    public Categorias toCategoria(CategoriaRequest categoriaDTO
    ) {
        Categorias categorias = new Categorias();
        categorias.setCategoria(categoriaDTO.categoria());
        return categorias;
    }

    public CategoriaResponse toCategoriaDTO(Categorias categorias) {
        return new CategoriaResponse(
                categorias.getId(),
                categorias.getCategoria()
        );
    }
}
