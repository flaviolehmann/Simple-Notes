package br.com.ledscolatina.backend.repository;

import br.com.ledscolatina.backend.domain.Nota;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotaRepository extends JpaRepository<Nota, Long> {
}
