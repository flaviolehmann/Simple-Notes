package br.com.ledscolatina.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Getter @Setter
public class Caderno {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min = 1, max = 255, message = "O nome do caderno deve conter pelo menos 1 e no máximo 255 caracteres.")
    private String nome;

    @Size(max = 5000, message = "A descrição deve conter um máximo de 5000 caracteres.")
    private String descricao;

    @JsonIgnoreProperties("caderno")
    @OneToMany(mappedBy = "caderno")
    private List<Nota> notas;

}
