package br.com.ledscolatina.backend.except.custom;

import lombok.Getter;

@Getter
public class CadernoNotFoundException extends RuntimeException {

    private Long idBuscado;

    public CadernoNotFoundException(Long idBuscado) {
        this.idBuscado = idBuscado;
    }
}
