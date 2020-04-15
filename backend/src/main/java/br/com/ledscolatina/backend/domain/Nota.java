package br.com.ledscolatina.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter @Setter
public class Nota {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min = 1, max = 255, message = "O título da nota deve conter entre 1 e 255 caracteres.")
    private String titulo;

    @Size(max = 5000, message = "A descrição da nota deve conter um máximo de 5000 caracteres.")
    private String descricao;

    @NotNull(message = "Toda nota deve pertencer a um caderno.")
    @JsonIgnoreProperties("notas")
    @ManyToOne
    @JoinColumn(name="caderno_id")
    private Caderno caderno;

}
