package com.example.livraria.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "livro")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "descricao")
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categorias categoria;

    @Column(name = "isbn")
    private String isbn;

    @ManyToMany(mappedBy = "favoritos")
    @JsonBackReference
    private Set<Usuario> usuariosFavoritos = new HashSet<>();

    @Column(name = "data_publicacao")
    private LocalDate dataPublicacao;

}
