package VittorioVescio.u5w2d4.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import VittorioVescio.u5w2d4.entities.User;
import VittorioVescio.u5w2d4.entities.payloads.UserRegistrationPayload;
import VittorioVescio.u5w2d4.exceptions.BadRequestException;
import VittorioVescio.u5w2d4.exceptions.NotFoundException;
import VittorioVescio.u5w2d4.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository usersRepository;

	public User findById(UUID id) throws NotFoundException {
		return usersRepository.findById(id).orElseThrow(() -> new NotFoundException("Utente non trovato!"));
	}

	public User findByEmail(String email) throws NotFoundException {
		return usersRepository.findByEmail(email).orElseThrow(
				() -> new NotFoundException("Non risulta registrato alcun utente con questo indirizzo email"));
	}

	public User create(UserRegistrationPayload u) {
		usersRepository.findByEmail(u.getEmail()).ifPresent(user -> {
			throw new BadRequestException("Email " + user.getEmail() + " already in use!");
		});
		User newUser = new User(u.getUsername(), u.getNome(), u.getEmail(), u.getPassword());
		return usersRepository.save(newUser);
	}
}
