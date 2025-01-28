package com.example.livraria.controller;

import com.example.livraria.dto.Livro.LivroRequest;
import com.example.livraria.dto.Livro.LivroResponse;
import com.example.livraria.mapper.LivroMapper;
import com.example.livraria.model.Livro;
import com.example.livraria.repositories.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.parser.Entity;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/livro/", produces = {"aplication/json"})
public class LivroController {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private LivroMapper livroMapper;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EntityModel<LivroResponse>>> getAllLivros() {
        List<Livro> livros = livroRepository.findAll();

        if (livros.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            List<EntityModel<LivroResponse>> livroResponse = livros.stream()
                    .map(livro -> {
                        LivroResponse livroDTO = livroMapper.toLivroDTO(livro);
                        return EntityModel.of(livroDTO,
                                linkTo(methodOn(LivroController.class).getAllLivros()).withSelfRel(),
                                linkTo(methodOn(LivroController.class).getLivroById(livro.getId())).withRel("getLivroById"),
                                linkTo(methodOn(LivroController.class).updateInfoLivro(livro.getId())).withRel("updateInfoLivro"),
                                linkTo(methodOn(LivroController.class).deleteLivro(livro.getId())).withRel("deleteLivro")
                        );
                    }).toList();

            return ResponseEntity.ok(livroResponse);
        }
    }

    @GetMapping(value = "{id_livro}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<LivroResponse>> getLivroById(@PathVariable Long id_livro) {

        Livro livro = livroRepository.findById(id_livro)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado"));


        LivroResponse livroDTO = livroMapper.toLivroDTO(livro);
        return new ResponseEntity<>(EntityModel.of(livroDTO,
                linkTo(methodOn(LivroController.class).getLivroById(id_livro)).withSelfRel(),
                linkTo(methodOn(LivroController.class).getAllLivros()).withRel("getAllLivros"),
                linkTo(methodOn(LivroController.class).updateInfoLivro(id_livro)).withRel("updateInfoLivro"),
                linkTo(methodOn(LivroController.class).deleteLivro(id_livro)).withRel("deleteLivro")),
                HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<LivroResponse>> createLivro(@RequestBody LivroRequest livroDTO) {

        Livro livro = livroMapper.toLivro(livroDTO);
        livroRepository.save(livro);
        LivroResponse livroResponse = livroMapper.toLivroDTO(livro);

        return new ResponseEntity<>(EntityModel.of(livroResponse,
                linkTo(methodOn(LivroController.class).createLivro(livroDTO)).withSelfRel(),
                linkTo(methodOn(LivroController.class).getLivroById(livro.getId())).withRel("getLivroById"),
                linkTo(methodOn(LivroController.class).getAllLivros()).withRel("getAllLivros"),
                linkTo(methodOn(LivroController.class).updateInfoLivro(livro.getId())).withRel("updateInfoLivro"),
                linkTo(methodOn(LivroController.class).deleteLivro(livro.getId())).withRel("deleteLivro")
        ), HttpStatus.CREATED);
    }

    @PutMapping(value = "{id_livro}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<LivroResponse>> updateInfoLivro(@PathVariable Long id_livro) {

        Livro livro = livroRepository.findById(id_livro)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado"));


        livroRepository.save(livro);
        LivroResponse livroDTO = livroMapper.toLivroDTO(livro);

        return new ResponseEntity<>(EntityModel.of(livroDTO,
                linkTo(methodOn(LivroController.class).getLivroById(id_livro)).withSelfRel(),
                linkTo(methodOn(LivroController.class).getAllLivros()).withRel("getAllLivros"),
                linkTo(methodOn(LivroController.class).updateInfoLivro(id_livro)).withRel("updateInfoLivro"),
                linkTo(methodOn(LivroController.class).deleteLivro(id_livro)).withRel("deleteLivro")
        ), HttpStatus.OK);
    }

    @DeleteMapping(value = "{id_livro}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<LivroResponse>> deleteLivro(@PathVariable Long id_livro) {

        Livro livro = livroRepository.findById(id_livro)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado"));

        livroRepository.delete(livro);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
