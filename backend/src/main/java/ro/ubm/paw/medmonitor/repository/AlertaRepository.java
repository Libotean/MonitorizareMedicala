package ro.ubm.paw.medmonitor.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ro.ubm.paw.medmonitor.model.Alerta;

import java.util.List;

public interface AlertaRepository extends JpaRepository<Alerta, Long> {

    @EntityGraph(attributePaths = {"masuratoare", "masuratoare.pacient"})
    @Query("""
            select a from Alerta a
            where a.citita = false
              and a.masuratoare.pacient in (select p from Medic m join m.pacienti p where m.id = :medicId)
            order by a.creatLa desc
            """)
    List<Alerta> findNecititeByMedicId(Long medicId);
}
