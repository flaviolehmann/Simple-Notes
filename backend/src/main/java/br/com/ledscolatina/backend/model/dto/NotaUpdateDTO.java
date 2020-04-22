package br.com.ledscolatina.backend.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NotaUpdateDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private CadernoIndexDTO caderno;
    private final LocalDateTime updatedAt = LocalDateTime.now();
}
