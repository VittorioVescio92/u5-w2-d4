package VittorioVescio.u5w2d4.repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import VittorioVescio.u5w2d4.entities.Postazione;
import VittorioVescio.u5w2d4.entities.Prenotazione;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, UUID> {
	Optional<Prenotazione> findByPostazioneAndDataPrenotata(Postazione postazione, LocalDate data);
}
