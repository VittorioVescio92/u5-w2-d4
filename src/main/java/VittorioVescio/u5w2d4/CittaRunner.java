package VittorioVescio.u5w2d4;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import VittorioVescio.u5w2d4.entities.Citta;
import VittorioVescio.u5w2d4.repository.CittaRepository;

@Component
public class CittaRunner implements CommandLineRunner {
	@Autowired
	CittaRepository cittaRepository;

	@Override
	public void run(String... args) throws Exception {
		Faker faker = new Faker(new Locale("it"));

		List<Citta> citiesDB = cittaRepository.findAll();
		if (citiesDB.size() == 0) {
			for (int i = 0; i < 20; i++) {
				try {
					String nome = faker.address().cityName();
					Citta citta = new Citta(nome);
					cittaRepository.save(citta);
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}
	}
}
