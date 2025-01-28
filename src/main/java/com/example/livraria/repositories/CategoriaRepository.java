package com.example.livraria.repositories;


import com.example.livraria.model.Categorias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface CategoriaRepository extends JpaRepository<Categorias, Long> {

    Categorias findCategoriasByCategoria(String categoria);
}
