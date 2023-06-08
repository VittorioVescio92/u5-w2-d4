package VittorioVescio.u5w2d4.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import VittorioVescio.u5w2d4.entities.Postazione;

@Repository
public interface PostazioneRepository extends JpaRepository<Postazione, UUID> {

}
