package br.com.ledscolatina.backend.service;

import br.com.ledscolatina.backend.except.custom.CadernoNotFoundException;
import br.com.ledscolatina.backend.except.custom.NotaNotFoundException;
import br.com.ledscolatina.backend.model.Nota;
import br.com.ledscolatina.backend.model.dto.NotaIndexDTO;
import br.com.ledscolatina.backend.model.dto.NotaShowDTO;
import br.com.ledscolatina.backend.repository.CadernoRepository;
import br.com.ledscolatina.backend.repository.NotaRepository;
import br.com.ledscolatina.backend.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotaService {

    @Autowired
    private NotaRepository notaRepository;

    @Autowired
    private CadernoRepository cadernoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<NotaIndexDTO> index() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            List<Nota> allNotas = new ArrayList<>();
            usuarioRepository.findByUsername(authentication.getName()).getCadernos()
                    .forEach(caderno -> allNotas.addAll(caderno.getNotas()));
            return allNotas.stream()
                    .map(nota -> modelMapper.map(nota, NotaIndexDTO.class))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public NotaShowDTO create(Nota nota) {
        Long idCadernoProcurado = nota.getCaderno().getId();
        return cadernoRepository.findById(idCadernoProcurado)
                .map(record -> {
                    nota.setCaderno(record);
                    return modelMapper.map(notaRepository.save(nota), NotaShowDTO.class);
                })
                .orElseThrow(() -> new CadernoNotFoundException(idCadernoProcurado));
    }

    public NotaShowDTO show(Long id) {
        return notaRepository.findById(id)
                .map(nota -> modelMapper.map(nota, NotaShowDTO.class))
                .orElseThrow(() -> new NotaNotFoundException(id));
    }

    public NotaShowDTO update(Nota nota) {
        return notaRepository.findById(nota.getId())
                .map(record -> {
                    cadernoRepository.findById(nota.getCaderno().getId())
                            .ifPresent(record::setCaderno);

                    record.setDescricao(nota.getDescricao());
                    record.setTitulo(nota.getTitulo());
                    return modelMapper.map(notaRepository.save(record), NotaShowDTO.class);
                })
                .orElseThrow(() -> new NotaNotFoundException(nota.getId()));
    }

    public void delete(Long id) {
        if (notaRepository.findById(id).isPresent()) {
            notaRepository.deleteById(id);
        }
        else {
            throw new NotaNotFoundException(id);
        }
    }
}
