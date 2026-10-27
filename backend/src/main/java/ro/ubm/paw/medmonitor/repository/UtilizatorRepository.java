package ro.ubm.paw.medmonitor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.ubm.paw.medmonitor.model.Utilizator;

import java.util.Optional;

public interface UtilizatorRepository extends JpaRepository<Utilizator, Long> {

    Optional<Utilizator> findByEmail(String email);

    boolean existsByEmail(String email);
}
