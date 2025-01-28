package com.example.livraria.controller;

import com.example.livraria.dto.Categoria.CategoriaRequest;
import com.example.livraria.dto.Categoria.CategoriaResponse;
import com.example.livraria.mapper.CategoriaMapper;
import com.example.livraria.model.Categorias;
import com.example.livraria.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/categorias", produces = {"aplication/json"})
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CategoriaMapper categoriaMapper;

    @GetMapping(value = "/",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EntityModel<CategoriaResponse>>> getAllCategorias()
    {
        List<Categorias> categorias = categoriaRepository.findAll();

        if (categorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            List<EntityModel<CategoriaResponse>> categoriaResponse = categorias.stream()
                    .map(categoria -> {
                        CategoriaResponse categoriaDTO = categoriaMapper.toCategoriaDTO(categoria);
                        return EntityModel.of(categoriaDTO,
                                linkTo(methodOn(CategoriaController.class).getAllCategorias()).withSelfRel(),
                                linkTo(methodOn(CategoriaController.class).getCategoriaById(categoria.getId())).withRel("getCategoriaById"),
                                linkTo(methodOn(CategoriaController.class).createCategoria(null)).withRel("createCategoria"),
                                linkTo(methodOn(CategoriaController.class).updateInfoCategoria(categoria.getId(), null)).withRel("updateInfoCategoria"),
                                linkTo(methodOn(CategoriaController.class).deleteCategoria(categoria.getId())).withRel("deleteCategoria")
                        );
                    }).toList();

            return new ResponseEntity<>(categoriaResponse, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/{id_categoria}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<CategoriaResponse>> getCategoriaById(@PathVariable Long id_categoria) {

        Categorias categoria = categoriaRepository.findById(id_categoria)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada"));

        CategoriaResponse categoriaDTO = categoriaMapper.toCategoriaDTO(categoria);

        return new ResponseEntity<>(EntityModel.of(categoriaDTO,
                linkTo(methodOn(CategoriaController.class).getAllCategorias()).withRel("getAllCategorias"),
                linkTo(methodOn(CategoriaController.class).getCategoriaById(id_categoria)).withSelfRel(),
                linkTo(methodOn(CategoriaController.class).createCategoria(null)).withRel("createCategoria"),
                linkTo(methodOn(CategoriaController.class).updateInfoCategoria(categoria.getId(),null)).withRel("updateInfoCategoria"),
                linkTo(methodOn(CategoriaController.class).deleteCategoria(id_categoria)).withRel("deleteCategoria")
        ), HttpStatus.OK);
    }

    @PutMapping(value = "/{id_categoria}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<CategoriaResponse>> updateInfoCategoria(@PathVariable Long id_categoria, @RequestBody CategoriaRequest categoriaDTO) {
        Categorias categoria = categoriaRepository.findById(id_categoria)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada"));

        categoria.setCategoria(categoriaDTO.categoria());

        categoriaRepository.save(categoria);

        CategoriaResponse categoriaResponse = categoriaMapper.toCategoriaDTO(categoria);

        return new ResponseEntity<>(EntityModel.of(categoriaResponse,
                linkTo(methodOn(CategoriaController.class).getAllCategorias()).withRel("getAllCategorias"),
                linkTo(methodOn(CategoriaController.class).getCategoriaById(id_categoria)).withSelfRel(),
                linkTo(methodOn(CategoriaController.class).createCategoria(null)).withRel("createCategoria"),
                linkTo(methodOn(CategoriaController.class).updateInfoCategoria(id_categoria, null)).withRel("updateInfoCategoria"),
                linkTo(methodOn(CategoriaController.class).deleteCategoria(id_categoria)).withRel("deleteCategoria")
        ), HttpStatus.OK);
    }

    @PostMapping(value = "/",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<CategoriaResponse>> createCategoria(@RequestBody CategoriaRequest categoriaDTO) {
        Categorias categoria = categoriaMapper.toCategoria(categoriaDTO);
        categoriaRepository.save(categoria);

        CategoriaResponse categoriaResponse = categoriaMapper.toCategoriaDTO(categoria);

        return new ResponseEntity<>(EntityModel.of(categoriaResponse,
                linkTo(methodOn(CategoriaController.class).getAllCategorias()).withRel("getAllCategorias"),
                linkTo(methodOn(CategoriaController.class).getCategoriaById(null)).withSelfRel(),
                linkTo(methodOn(CategoriaController.class).createCategoria(null)).withRel("createCategoria"),
                linkTo(methodOn(CategoriaController.class).updateInfoCategoria(null, null)).withRel("updateInfoCategoria"),
                linkTo(methodOn(CategoriaController.class).deleteCategoria(null)).withRel("deleteCategoria")
        ), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id_categoria}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<CategoriaResponse>> deleteCategoria(@PathVariable Long id_categoria)
    {
        Categorias categoria = categoriaRepository.findById(id_categoria)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada"));

        categoriaRepository.delete(categoria);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
