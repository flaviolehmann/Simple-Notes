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
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O nome do usuário é obrigatório.")
    @Size(min = 3, max = 100, message = "O nome do usuário deve conter entre 3 e 100 caracteres.")
    private String username;

    @NotNull(message = "A senha do usuário é obrigatória.")
    @Size(min = 5, max = 5000, message = "A senha do usuário deve conter pelo menos 5 caracteres.")
    private String password;

    @OneToMany(mappedBy = "usuario")
    private List<Caderno> cadernos;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
