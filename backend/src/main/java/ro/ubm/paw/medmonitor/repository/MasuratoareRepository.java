package ro.ubm.paw.medmonitor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.ubm.paw.medmonitor.model.Masuratoare;

import java.util.List;

public interface MasuratoareRepository extends JpaRepository<Masuratoare, Long> {

    List<Masuratoare> findByPacientIdOrderByMasuratLaDesc(Long pacientId);
}
