package VittorioVescio.u5w2d4;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import VittorioVescio.u5w2d4.entities.User;
import VittorioVescio.u5w2d4.repository.UserRepository;

@Component
public class UsersRunner implements CommandLineRunner {
	@Autowired
	UserRepository userRepository;

	@Override
	public void run(String... args) throws Exception {
		Faker faker = new Faker(new Locale("it"));

		List<User> usersDB = userRepository.findAll();
		if (usersDB.size() == 0) {
			for (int i = 0; i < 10; i++) {
				try {
					String username = faker.name().username();
					String nome = faker.name().firstName();
					String psw = faker.internet().password();
					String email = faker.internet().emailAddress();
					User user = new User(username, nome, email, psw);
					userRepository.save(user);
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}
	}
}
