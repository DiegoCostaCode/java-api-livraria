package com.example.livraria.repositories;

import com.example.livraria.model.Categorias;
import com.example.livraria.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {


    Optional<Livro> findByCategoria(Categorias categorias);

    Optional<Livro> findByTitulo(String titulo);
}
