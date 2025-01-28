package com.example.livraria.mapper;

import com.example.livraria.dto.Usuario.UsuarioRequest;
import com.example.livraria.dto.Usuario.UsuarioResponse;
import com.example.livraria.model.Usuario;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UsuarioMapper {

    public Usuario toUsuario(UsuarioRequest usuarioDTO) {
        Usuario usuario = new Usuario();

        usuario.setNome(usuarioDTO.nome());
        usuario.setEmail(usuarioDTO.email());
        usuario.setSince(LocalDateTime.now());
        return usuario;
    }

    public UsuarioResponse toUsuarioDTO(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getFavoritos(),
                usuario.getSince().toString()
        );
    }
}
