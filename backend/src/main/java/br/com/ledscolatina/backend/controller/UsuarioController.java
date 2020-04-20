package br.com.ledscolatina.backend.controller;

import br.com.ledscolatina.backend.model.Usuario;
import br.com.ledscolatina.backend.model.dto.*;
import br.com.ledscolatina.backend.repository.UsuarioRepository;
import br.com.ledscolatina.backend.service.UsuarioService;
import br.com.ledscolatina.backend.util.DTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("inscrever-se")
    private ResponseEntity<?> create(@DTO(UsuarioCreationDTO.class) Usuario usuario) {
        usuarioService.create(usuario);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    private ResponseEntity<?> index() {
        return ResponseEntity.ok(usuarioService.index());
    }

    @PostMapping("alterar-senha")
    private ResponseEntity<?> update(@DTO(UsuarioUpdateDTO.class) Usuario usuario) {
        return ResponseEntity.ok(usuarioService.update(usuario));
    }

}
