package VittorioVescio.u5w2d4.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import VittorioVescio.u5w2d4.auth.payloads.AuthenticationSuccessfullPayload;
import VittorioVescio.u5w2d4.entities.User;
import VittorioVescio.u5w2d4.entities.payloads.UserLoginPayload;
import VittorioVescio.u5w2d4.entities.payloads.UserRegistrationPayload;
import VittorioVescio.u5w2d4.exceptions.NotFoundException;
import VittorioVescio.u5w2d4.exceptions.UnauthorizedException;
import VittorioVescio.u5w2d4.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	UserService usersService;

	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody @Validated UserRegistrationPayload body) {
		User createdUser = usersService.create(body);
		return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<AuthenticationSuccessfullPayload> login(@RequestBody UserLoginPayload body)
			throws NotFoundException {

		// 1. Verificare che l'email dell'utente sia presente nel db
		User user = usersService.findByEmail(body.getEmail());
		// 2. In caso affermativo devo verificare che la pw corrisponda a quella trovata
		// nel db
		if (!body.getPassword().matches(user.getPassword()))
			throw new UnauthorizedException("Credenziali non valide");
		// 3. Se tutto ok --> genero
		String token = JWTTools.createToken(user);
		// 4. Altrimenti --> 401 ("Credenziali non valide")

		return new ResponseEntity<>(new AuthenticationSuccessfullPayload(token), HttpStatus.OK);
	}

}
