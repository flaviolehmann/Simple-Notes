package br.com.ledscolatina.backend.model.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CadernoShowDTO {
    private Long id;
    private String nome;
    private String descricao;
    private List<NotaIndexDTO> notas;
    private UsuarioIndexDTO usuario;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
