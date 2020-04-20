package br.com.ledscolatina.backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
public class Caderno {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "O nome do caderno é obrigatório.")
    @Size(min = 1, max = 255, message = "O nome do caderno deve conter pelo menos 1 e no máximo 255 caracteres.")
    private String nome;

    @Size(max = 5000, message = "A descrição deve conter um máximo de 5000 caracteres.")
    private String descricao;

    @OneToMany(mappedBy = "caderno", cascade = CascadeType.ALL)
    private List<Nota> notas;

    @NotNull(message = "Todo caderno deve pertencer a um usuário.")
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
