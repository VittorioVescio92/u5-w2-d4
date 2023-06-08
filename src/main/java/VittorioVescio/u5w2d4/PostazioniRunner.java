package VittorioVescio.u5w2d4;

import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import VittorioVescio.u5w2d4.entities.Edificio;
import VittorioVescio.u5w2d4.entities.Postazione;
import VittorioVescio.u5w2d4.entities.TipoPostazione;
import VittorioVescio.u5w2d4.repository.EdificioRepository;
import VittorioVescio.u5w2d4.repository.PostazioneRepository;

@Component
public class PostazioniRunner implements CommandLineRunner {
	@Autowired
	EdificioRepository edificioRepository;
	@Autowired
	PostazioneRepository postazioneRepository;

	@Override
	public void run(String... args) throws Exception {
		Faker faker = new Faker(new Locale("it"));

		List<Edificio> edificiDB = edificioRepository.findAll();
		List<Postazione> postazioniDB = postazioneRepository.findAll();

		if (postazioniDB.size() == 0) {
			for (int i = 0; i < 20; i++) {
				try {
					String descrizione = faker.lorem().sentence();
					int numeroMaxOccupanti = faker.number().numberBetween(25, 100);
					Random random = new Random();
					int randomIndex = random.nextInt(TipoPostazione.values().length);
					TipoPostazione tipo = TipoPostazione.values()[randomIndex];
					int randomIndex2 = new Random().nextInt(edificiDB.size());
					Edificio rndmEdificio = edificiDB.get(randomIndex2);
					Postazione postazione = new Postazione(descrizione, numeroMaxOccupanti, tipo, rndmEdificio);
					postazioneRepository.save(postazione);
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}
	}
}
