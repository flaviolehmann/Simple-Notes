package br.com.ledscolatina.backend.repository;

import br.com.ledscolatina.backend.domain.Caderno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CadernoRepository extends JpaRepository<Caderno, Long> {
}
