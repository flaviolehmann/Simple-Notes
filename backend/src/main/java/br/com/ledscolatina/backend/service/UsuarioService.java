package br.com.ledscolatina.backend.service;

import br.com.ledscolatina.backend.except.custom.UsuarioNotFoundException;
import br.com.ledscolatina.backend.model.Usuario;
import br.com.ledscolatina.backend.model.dto.UsuarioIndexDTO;
import br.com.ledscolatina.backend.model.dto.UsuarioShowDTO;
import br.com.ledscolatina.backend.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    public UsuarioShowDTO create(Usuario usuario) {
        usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));
        return modelMapper.map(usuarioRepository.save(usuario), UsuarioShowDTO.class);
    }

    public List<UsuarioIndexDTO> index() {
        return usuarioRepository.findAll().stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioIndexDTO.class))
                .collect(Collectors.toList());
    }

    public UsuarioShowDTO update(Usuario usuario) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            Usuario updatingUsuario = usuarioRepository.findByUsername(authentication.getName());
            updatingUsuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));
            return modelMapper.map(usuarioRepository.save(updatingUsuario), UsuarioShowDTO.class);
        }
        throw new UsuarioNotFoundException();
    }
}
