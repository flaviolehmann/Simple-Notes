package br.com.ledscolatina.backend.service;

import br.com.ledscolatina.backend.except.custom.CadernoNotFoundException;
import br.com.ledscolatina.backend.except.custom.UsuarioNotFoundException;
import br.com.ledscolatina.backend.model.Caderno;
import br.com.ledscolatina.backend.model.Usuario;
import br.com.ledscolatina.backend.model.dto.CadernoIndexDTO;
import br.com.ledscolatina.backend.model.dto.CadernoShowDTO;
import br.com.ledscolatina.backend.repository.CadernoRepository;
import br.com.ledscolatina.backend.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CadernoService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CadernoRepository cadernoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public CadernoShowDTO create(Caderno caderno) {
        Usuario usuario = usuarioRepository.findByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()
        );
        caderno.setNotas(new ArrayList<>());
        caderno.setUsuario(usuario);

        return modelMapper.map(cadernoRepository.save(caderno), CadernoShowDTO.class);
    }

    public List<CadernoIndexDTO> index() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return usuarioRepository.findByUsername(authentication.getName()).getCadernos().stream()
                    .map(caderno -> modelMapper.map(caderno, CadernoIndexDTO.class))
                    .collect(Collectors.toList());
        }
        throw new UsuarioNotFoundException();
    }

    public CadernoShowDTO show(Long id) {
        return cadernoRepository.findById(id)
                .map(record -> modelMapper.map(record, CadernoShowDTO.class))
                .orElseThrow(() -> new CadernoNotFoundException(id));
    }

    public CadernoShowDTO update(Caderno caderno) {
        return cadernoRepository.findById(caderno.getId())
                .map(record -> {
                    record.setNome(caderno.getNome());
                    record.setDescricao(caderno.getDescricao());
                    record.setUpdatedAt(caderno.getUpdatedAt());
                    return modelMapper.map(cadernoRepository.save(record), CadernoShowDTO.class);
                }).orElseThrow(() -> new CadernoNotFoundException(caderno.getId()));
    }

    public void delete(Long id) {
        if (cadernoRepository.findById(id).isPresent()) {
            cadernoRepository.deleteById(id);
        }
        else {
            throw new CadernoNotFoundException(id);
        }
    }

}
