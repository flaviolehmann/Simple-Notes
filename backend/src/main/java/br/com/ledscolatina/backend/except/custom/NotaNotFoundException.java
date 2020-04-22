package br.com.ledscolatina.backend.except.custom;

import lombok.Getter;

@Getter
public class NotaNotFoundException extends RuntimeException{

    private Long idBuscado;

    public NotaNotFoundException(Long idBuscado) {
        this.idBuscado = idBuscado;
    }

}
