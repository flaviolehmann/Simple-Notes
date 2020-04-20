package br.com.ledscolatina.backend.controller;

import br.com.ledscolatina.backend.model.Nota;
import br.com.ledscolatina.backend.except.custom.CadernoNotFoundException;
import br.com.ledscolatina.backend.model.dto.*;
import br.com.ledscolatina.backend.repository.CadernoRepository;
import br.com.ledscolatina.backend.repository.NotaRepository;
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
@RequestMapping("notas")
public class NotaController {

    @Autowired
    private NotaRepository notaRepository;

    @Autowired
    private CadernoRepository cadernoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<?> index() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            List<Nota> allNotas = new ArrayList<>();
            usuarioRepository.findByUsername(authentication.getName()).getCadernos()
                    .forEach(caderno -> allNotas.addAll(caderno.getNotas()));
            return ResponseEntity.ok(
                    allNotas.stream().map(nota -> modelMapper.map(nota, NotaIndexDTO.class)).collect(Collectors.toList())
            );
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> create(@DTO(NotaCreationDTO.class) Nota nota) {
        Long idCadernoProcurado = nota.getCaderno().getId();
        return cadernoRepository.findById(idCadernoProcurado)
                .map(record -> {
                    nota.setCaderno(record);
                    return ResponseEntity.ok(modelMapper.map(notaRepository.save(nota), NotaShowDTO.class));
                })
                .orElseThrow(() -> new CadernoNotFoundException(idCadernoProcurado));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        return notaRepository.findById(id)
                .map(nota -> ResponseEntity.ok(modelMapper.map(nota, NotaShowDTO.class)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @DTO(NotaUpdateDTO.class) Nota nota) {
        return notaRepository.findById(id)
                .map(record -> {
                    cadernoRepository.findById(nota.getCaderno().getId())
                            .ifPresent(record::setCaderno);

                    record.setDescricao(nota.getDescricao());
                    record.setTitulo(nota.getTitulo());
                    return ResponseEntity.ok(modelMapper.map(notaRepository.save(record), NotaShowDTO.class));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return notaRepository.findById(id)
                .map(record -> {
                    notaRepository.deleteById(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
