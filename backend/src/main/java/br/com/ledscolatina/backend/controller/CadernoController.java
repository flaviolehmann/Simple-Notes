package br.com.ledscolatina.backend.controller;

import br.com.ledscolatina.backend.model.Caderno;
import br.com.ledscolatina.backend.model.dto.*;
import br.com.ledscolatina.backend.service.CadernoService;
import br.com.ledscolatina.backend.util.DTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("cadernos")
public class CadernoController {

    @Autowired
    private CadernoService cadernoService;

    @PostMapping
    public ResponseEntity<?> create(@DTO(CadernoCreationDTO.class) Caderno caderno) {
        return ResponseEntity.ok(cadernoService.create(caderno));
    }

    @GetMapping
    public ResponseEntity<?> index() {
        return ResponseEntity.ok(cadernoService.index());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        return ResponseEntity.ok(cadernoService.show(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                                    @DTO(CadernoUpdateDTO.class) Caderno caderno) {
        caderno.setId(id);
        return ResponseEntity.ok(cadernoService.update(caderno));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        cadernoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
