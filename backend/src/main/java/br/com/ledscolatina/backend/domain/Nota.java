package br.com.ledscolatina.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Nota {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String titulo;
    private String descricao;

    @JsonIgnoreProperties("notas")
    @ManyToOne
    @JoinColumn(name="caderno_id")
    private Caderno caderno;

}
