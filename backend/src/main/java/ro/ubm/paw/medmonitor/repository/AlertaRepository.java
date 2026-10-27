package ro.ubm.paw.medmonitor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.ubm.paw.medmonitor.model.Alerta;

public interface AlertaRepository extends JpaRepository<Alerta, Long> {
}
