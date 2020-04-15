package br.com.ledscolatina.backend.controller;

import br.com.ledscolatina.backend.domain.Caderno;
import br.com.ledscolatina.backend.repository.CadernoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("cadernos")
public class CadernoController {

    @Autowired
    private CadernoRepository cadernoRepository;

    @GetMapping
    public List<Caderno> index() {
        return cadernoRepository.findAll();
    }

    @PostMapping
    public Caderno create(@Valid @RequestBody Caderno caderno) {
        return cadernoRepository.save(caderno);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        return cadernoRepository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Caderno caderno) {
        return cadernoRepository.findById(id)
                .map(record -> {
                    record.setNome(caderno.getNome());
                    record.setDescricao(caderno.getDescricao());
                    Caderno updated = cadernoRepository.save(record);
                    return ResponseEntity.ok().body(updated);
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
