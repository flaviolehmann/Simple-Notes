package br.com.ledscolatina.backend.controller;

import br.com.ledscolatina.backend.model.Caderno;
import br.com.ledscolatina.backend.model.Usuario;
import br.com.ledscolatina.backend.model.dto.*;
import br.com.ledscolatina.backend.repository.CadernoRepository;
import br.com.ledscolatina.backend.repository.UsuarioRepository;
import br.com.ledscolatina.backend.util.DTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("cadernos")
public class CadernoController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CadernoRepository cadernoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<?> create(@DTO(CadernoCreationDTO.class) Caderno caderno) {
        Usuario usuario = usuarioRepository.findByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()
        );
        caderno.setNotas(new ArrayList<>());
        caderno.setUsuario(usuario);

        CadernoShowDTO createdCaderno = modelMapper.map(cadernoRepository.save(caderno), CadernoShowDTO.class);
        return ResponseEntity.ok(createdCaderno);
    }

    @GetMapping
    public ResponseEntity<?> index() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            List<CadernoIndexDTO> cadernos = usuarioRepository.findByUsername(authentication.getName()).getCadernos()
                    .stream().map(caderno -> modelMapper.map(caderno, CadernoIndexDTO.class)).collect(Collectors.toList());

            return ResponseEntity.ok(cadernos);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        return cadernoRepository.findById(id)
                .map(record -> ResponseEntity.ok().body(modelMapper.map(record, CadernoShowDTO.class)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                                    @DTO(CadernoUpdateDTO.class) Caderno caderno) {
        return cadernoRepository.findById(id)
                .map(record -> {
                    record.setNome(caderno.getNome());
                    record.setDescricao(caderno.getDescricao());
                    record.setUpdatedAt(caderno.getUpdatedAt());
                    CadernoShowDTO updatedCaderno = modelMapper.map(cadernoRepository.save(record), CadernoShowDTO.class);
                    return ResponseEntity.ok(updatedCaderno);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return cadernoRepository.findById(id)
                .map(record -> {
                    cadernoRepository.deleteById(id);
                    return ResponseEntity.noContent().build();
                }).orElse(ResponseEntity.notFound().build());
    }


}
