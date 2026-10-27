package ro.ubm.paw.medmonitor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.ubm.paw.medmonitor.model.Pacient;

public interface PacientRepository extends JpaRepository<Pacient, Long> {
}
