package com.example.livraria.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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

    @ManyToMany
    @JoinTable(name = "favoritos",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "livro_id")
    )
    private Set<Livro> favoritos = new HashSet<>();

    @Column(name = "since")
    private LocalDateTime since;

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
