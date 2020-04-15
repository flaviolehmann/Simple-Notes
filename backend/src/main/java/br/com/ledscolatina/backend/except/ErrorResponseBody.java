package br.com.ledscolatina.backend.except;

import lombok.Data;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Data
public class ErrorResponseBody {

    private Date timestamp;
    private int status;
    private List<String> errors;
    private List<String> fields;

    public ErrorResponseBody(int status, List<String> errors, List<String> fields) {
        this.timestamp = new Date();
        this.status = status;
        this.errors = errors;
        this.fields = fields;
    }

    public ErrorResponseBody(int status, String error, String field) {
        List<String> listaDeErros = Collections.singletonList(error);
        List<String> listaDeFields = Collections.singletonList(field);

        this.timestamp = new Date();
        this.status = status;
        this.errors = listaDeErros;
        this.fields = listaDeFields;
    }

}
