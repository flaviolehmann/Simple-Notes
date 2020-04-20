package br.com.ledscolatina.backend.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CadernoUpdateDTO {
    private Long id;
    private String nome;
    private String descricao;
    private final LocalDateTime updatedAt = LocalDateTime.now();
}
