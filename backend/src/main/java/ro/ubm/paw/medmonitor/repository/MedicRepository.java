package ro.ubm.paw.medmonitor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ro.ubm.paw.medmonitor.model.Medic;

public interface MedicRepository extends JpaRepository<Medic, Long> {

    boolean existsByIdAndUtilizatorEmail(Long id, String email);

    @Query("select count(p) > 0 from Medic m join m.pacienti p where m.utilizator.email = :email and p.id = :pacientId")
    boolean urmarestePacient(String email, Long pacientId);
}
