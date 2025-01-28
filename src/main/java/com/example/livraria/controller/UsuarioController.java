package com.example.livraria.controller;

import com.example.livraria.dto.Usuario.UsuarioRequest;
import com.example.livraria.dto.Usuario.UsuarioResponse;
import com.example.livraria.mapper.UsuarioMapper;
import com.example.livraria.model.Livro;
import com.example.livraria.model.Usuario;
import com.example.livraria.repositories.LivroRepository;
import com.example.livraria.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/usuario/", produces = {"aplication/json"})
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private LivroRepository livroRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EntityModel<UsuarioResponse>>> getAll() {
        List<Usuario> usuarios = usuarioRepository.findAll();

        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            List<EntityModel<UsuarioResponse>> usuarioResponse = usuarios.stream()
                    .map(usuario -> {
                        UsuarioResponse usuarioDTO = usuarioMapper.toUsuarioDTO(usuario);
                        return EntityModel.of(usuarioDTO,
                                linkTo(methodOn(UsuarioController.class).getAll()).withSelfRel(),
                                linkTo(methodOn(UsuarioController.class).getById(usuario.getId())).withRel("getUsuarioById"),
                                linkTo(methodOn(UsuarioController.class).createUser (null)).withRel("createUsuario"),
                                linkTo(methodOn(UsuarioController.class).updateInfo(usuario.getId())).withRel("updateUsuario"),
                                linkTo(methodOn(UsuarioController.class).deleteUser (usuario.getId())).withRel("deleteUsuario"),
                                linkTo(methodOn(UsuarioController.class).addToFavoritos(usuario.getId(), null)).withRel("addLivroToFavoritos"),
                                linkTo(methodOn(UsuarioController.class).removeFromFavoritos(usuario.getId(), null)).withRel("removeLivroFromFavoritos")
                        );
                    }).collect(Collectors.toList());

            return new ResponseEntity<>(usuarioResponse, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<UsuarioResponse>> getById(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        UsuarioResponse usuarioDTO = usuarioMapper.toUsuarioDTO(usuario);
        return new ResponseEntity<>(EntityModel.of(usuarioDTO,
                linkTo(methodOn(UsuarioController.class).getById(id)).withSelfRel(),
                linkTo(methodOn(UsuarioController.class).getAll()).withRel("getAllUsuarios"),
                linkTo(methodOn(UsuarioController.class).createUser (null)).withRel("createUsuario"),
                linkTo(methodOn(UsuarioController.class).updateInfo(id)).withRel("updateUsuario"),
                linkTo(methodOn(UsuarioController.class).deleteUser (id)).withRel("deleteUsuario"),
                linkTo(methodOn(UsuarioController.class).addToFavoritos(usuario.getId(), null)).withRel("addLivroToFavoritos"),
                linkTo(methodOn(UsuarioController.class).removeFromFavoritos(usuario.getId(), null)).withRel("removeLivroFromFavoritos")
        ), HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<UsuarioResponse>> createUser (@RequestBody UsuarioRequest usuarioRequest) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuarioRequest.email());

        if (usuarioExistente.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Usuario usuario = usuarioMapper.toUsuario(usuarioRequest);
        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        UsuarioResponse usuarioDTO = usuarioMapper.toUsuarioDTO(usuarioSalvo);

        return new ResponseEntity<>(EntityModel.of(usuarioDTO,
                        linkTo(methodOn(UsuarioController.class).getById(usuarioSalvo.getId())).withSelfRel(),
                        linkTo(methodOn(UsuarioController.class).getAll()).withRel("getAllUsuarios"),
                        linkTo(methodOn(UsuarioController.class).updateInfo(usuarioSalvo.getId())).withRel("updateUsuario"),
                        linkTo(methodOn(UsuarioController.class).deleteUser (usuarioSalvo.getId())).withRel("deleteUsuario"),
                        linkTo(methodOn(UsuarioController.class).addToFavoritos(usuarioSalvo.getId(), null)).withRel("addLivroToFavoritos"),
                        linkTo(methodOn(UsuarioController.class).removeFromFavoritos(usuarioSalvo.getId(), null)).withRel("removeLivroFromFavoritos")
                ), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id_usuario}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<UsuarioResponse>> updateInfo(@PathVariable Long id_usuario) {
        Usuario usuario = usuarioRepository.findById(id_usuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        usuarioRepository.save(usuario);
        UsuarioResponse usuarioDTO = usuarioMapper.toUsuarioDTO(usuario);

        return new ResponseEntity<>(EntityModel.of(usuarioDTO,
                linkTo(methodOn( UsuarioController.class).updateInfo(id_usuario)).withSelfRel(),
                linkTo(methodOn(UsuarioController.class).getAll()).withRel("getAllUsuarios"),
                linkTo(methodOn(UsuarioController.class).getById(id_usuario)).withRel("getUsuarioById"),
                linkTo(methodOn(UsuarioController.class).deleteUser (id_usuario)).withRel("deleteUsuario"),
                linkTo(methodOn(UsuarioController.class).addToFavoritos(usuario.getId(), null)).withRel("addLivroToFavoritos"),
                linkTo(methodOn(UsuarioController.class).removeFromFavoritos(usuario.getId(), null)).withRel("removeLivroFromFavoritos")
        ), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id_usuario}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteUser (@PathVariable Long id_usuario) {
        Usuario usuario = usuarioRepository.findById(id_usuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        usuarioRepository.delete(usuario);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id_usuario}/{id_livro}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> removeFromFavoritos(@PathVariable Long id_usuario, @PathVariable Long id_livro) {
        Usuario usuario = usuarioRepository.findById(id_usuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        Livro livro = livroRepository.findById(id_livro)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado"));

        if (usuario.removerFavorito(livro)) {
            usuarioRepository.save(usuario);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping(value = "/{id_usuario}/{id_livro}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<UsuarioResponse>> addToFavoritos(@PathVariable Long id_usuario, @PathVariable Long id_livro) {
        Usuario usuario = usuarioRepository.findById(id_usuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        Livro livro = livroRepository.findById(id_livro)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado"));

        if (usuario.adicionarFavorito(livro)) {
            usuarioRepository.save(usuario);
            UsuarioResponse usuarioDTO = usuarioMapper.toUsuarioDTO(usuario);

            return new ResponseEntity<>(EntityModel.of(usuarioDTO,
                    linkTo(methodOn(UsuarioController.class).addToFavoritos(id_usuario, id_livro)).withSelfRel(),
                    linkTo(methodOn(UsuarioController.class).removeFromFavoritos(usuario.getId(), null)).withRel("removeLivroFromFavoritos"),
                    linkTo(methodOn(UsuarioController.class).getAll()).withRel("getAllUsuarios"),
                    linkTo(methodOn(UsuarioController.class).getById(id_usuario)).withRel("getUsuarioById"),
                    linkTo(methodOn(UsuarioController.class).updateInfo(id_usuario)).withRel("updateUsuario"),
                    linkTo(methodOn(UsuarioController.class).deleteUser (id_usuario)).withRel("deleteUsuario")
            ), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
