package br.com.ledscolatina.backend.model.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UsuarioShowDTO {
    private String username;
    private List<CadernoIndexDTO> cadernos;
}
