package ro.ubm.paw.medmonitor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ro.ubm.paw.medmonitor.model.Pacient;

import java.util.List;

public interface PacientRepository extends JpaRepository<Pacient, Long> {

    @Query("select p from Medic m join m.pacienti p where m.id = :medicId order by p.nume, p.prenume")
    List<Pacient> findByMedicId(Long medicId);

    boolean existsByIdAndUtilizatorEmail(Long id, String email);
}
