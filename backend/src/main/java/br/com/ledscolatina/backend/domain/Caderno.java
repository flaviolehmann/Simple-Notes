package br.com.ledscolatina.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
public class Caderno {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;
    private String descricao;

    @JsonIgnoreProperties("caderno")
    @OneToMany(mappedBy = "caderno")
    private List<Nota> notas;

}
