package br.com.ledscolatina.backend.controller;

import br.com.ledscolatina.backend.model.Usuario;
import br.com.ledscolatina.backend.model.dto.*;
import br.com.ledscolatina.backend.repository.UsuarioRepository;
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
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    private ResponseEntity<?> index() {
        List<UsuarioIndexDTO> usuarios = usuarioRepository.findAll().stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioIndexDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping("inscrever-se")
    private ResponseEntity<?> signUp(@DTO(UsuarioCreationDTO.class) Usuario usuario) {
        usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));
        UsuarioShowDTO savedUsuario = modelMapper.map(usuarioRepository.save(usuario), UsuarioShowDTO.class);
        return ResponseEntity.ok().build();
    }

    @PostMapping("alterar-senha")
    private ResponseEntity<?> alterarSenha(@DTO(UsuarioUpdateDTO.class) Usuario usuario) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            Usuario updatingUsuario = usuarioRepository.findByUsername(authentication.getName());
            updatingUsuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));
            updatingUsuario = usuarioRepository.save(updatingUsuario);

            return ResponseEntity.ok(modelMapper.map(updatingUsuario, UsuarioShowDTO.class));
        }
        return ResponseEntity.notFound().build();
    }

}
