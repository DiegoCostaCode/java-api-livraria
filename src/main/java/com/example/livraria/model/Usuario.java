package com.example.livraria.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome")
    private String nome;
    @Column(name = "email")
    private String email;
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;
    @Column(name = "since")
    private LocalDateTime since;
    @ManyToMany
    @JoinTable(name = "favoritos",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "livro_id")
    )
    @JsonManagedReference
    private Set<Livro> favoritos = new HashSet<>();


    public boolean adicionarFavorito(Livro livro) {
        if (!favoritos.contains(livro)) {
            favoritos.add(livro);
            return true;
        } else {
            System.out.println("O livro já está na lista de favoritos.");
            return false;
        }
    }

    public boolean removerFavorito(Livro livro) {
        if (favoritos.contains(livro)) {
            favoritos.remove(livro);
            System.out.println("Livro removido da lista de favoritos.");
            return true;
        } else {
            System.out.println("O livro não existe na lista de favoritos.");
            return false;
        }
    }



}
