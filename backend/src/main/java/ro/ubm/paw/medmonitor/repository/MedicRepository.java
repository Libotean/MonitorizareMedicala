package ro.ubm.paw.medmonitor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.ubm.paw.medmonitor.model.Medic;

public interface MedicRepository extends JpaRepository<Medic, Long> {
}
