package VittorioVescio.u5w2d4;

import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import VittorioVescio.u5w2d4.entities.Citta;
import VittorioVescio.u5w2d4.entities.Edificio;
import VittorioVescio.u5w2d4.repository.CittaRepository;
import VittorioVescio.u5w2d4.repository.EdificioRepository;

@Component
public class EdificioRunner implements CommandLineRunner {
	@Autowired
	EdificioRepository edificioRepository;
	@Autowired
	CittaRepository cittaRepository;

	@Override
	public void run(String... args) throws Exception {
		Faker faker = new Faker(new Locale("it"));

		List<Edificio> edificiDB = edificioRepository.findAll();
		List<Citta> cittaDB = cittaRepository.findAll();

		if (edificiDB.size() == 0) {
			for (int i = 0; i < 20; i++) {
				try {
					String nome = faker.animal().name();
					String indirizzo = faker.address().fullAddress();
					int randomIndex = new Random().nextInt(cittaDB.size());
					Citta cittaRandom = cittaDB.get(randomIndex);
					Edificio edificio = new Edificio(nome, indirizzo, cittaRandom);
					edificioRepository.save(edificio);
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}
	}
}
