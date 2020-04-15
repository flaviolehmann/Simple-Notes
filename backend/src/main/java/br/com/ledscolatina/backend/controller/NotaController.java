package br.com.ledscolatina.backend.controller;

import br.com.ledscolatina.backend.domain.Caderno;
import br.com.ledscolatina.backend.domain.Nota;
import br.com.ledscolatina.backend.except.custom.CadernoNotFoundException;
import br.com.ledscolatina.backend.repository.CadernoRepository;
import br.com.ledscolatina.backend.repository.NotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("notas")
public class NotaController {

    @Autowired
    private NotaRepository notaRepository;

    @Autowired
    private CadernoRepository cadernoRepository;

    @GetMapping
    public List<Nota> index() {
        return notaRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Nota nota) {
        Long idCadernoProcurado = nota.getCaderno().getId();
        return cadernoRepository.findById(idCadernoProcurado)
                .map(record -> {
                    nota.setCaderno(record);
                    return ResponseEntity.ok(notaRepository.save(nota));
                })
                .orElseThrow(() -> new CadernoNotFoundException(idCadernoProcurado));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        return notaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Nota nota) {
        return notaRepository.findById(id)
                .map(record -> {
                    cadernoRepository.findById(nota.getCaderno().getId())
                            .ifPresent(record::setCaderno);

                    record.setDescricao(nota.getDescricao());
                    record.setTitulo(nota.getTitulo());
                    Nota updated = notaRepository.save(record);
                    return ResponseEntity.ok(updated);
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
