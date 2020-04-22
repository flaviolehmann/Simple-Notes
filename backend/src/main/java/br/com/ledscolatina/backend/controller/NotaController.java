package br.com.ledscolatina.backend.controller;

import br.com.ledscolatina.backend.model.Nota;
import br.com.ledscolatina.backend.model.dto.*;
import br.com.ledscolatina.backend.service.NotaService;
import br.com.ledscolatina.backend.util.DTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("notas")
public class NotaController {

    @Autowired
    private NotaService notaService;

    @GetMapping
    public ResponseEntity<?> index() {
        return ResponseEntity.ok(notaService.index());
    }

    @PostMapping
    public ResponseEntity<?> create(@DTO(NotaCreationDTO.class) Nota nota) {
        return ResponseEntity.ok(notaService.create(nota));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        return ResponseEntity.ok(notaService.show(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @DTO(NotaUpdateDTO.class) Nota nota) {
        nota.setId(id);
        return ResponseEntity.ok(notaService.update(nota));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        notaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
