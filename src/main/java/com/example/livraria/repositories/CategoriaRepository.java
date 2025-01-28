package com.example.livraria.repositories;


import com.example.livraria.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
