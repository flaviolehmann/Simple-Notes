package br.com.ledscolatina.backend.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UsuarioUpdateDTO {
    private String password;
    private final LocalDateTime updatedAt = LocalDateTime.now();
}
