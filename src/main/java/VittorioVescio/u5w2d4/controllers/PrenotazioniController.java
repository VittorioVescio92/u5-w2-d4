package VittorioVescio.u5w2d4.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import VittorioVescio.u5w2d4.entities.Prenotazione;
import VittorioVescio.u5w2d4.entities.payloads.BookingPayload;
import VittorioVescio.u5w2d4.services.PrenotazioneService;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioniController {
	@Autowired
	private PrenotazioneService prenotazioneService;

	@GetMapping("")
	public Page<Prenotazione> getPrenotazioni(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
		return prenotazioneService.find(page, size, sortBy);
	}

	@GetMapping("/{id}")
	public Prenotazione getById(@PathVariable UUID id) {
		return prenotazioneService.findById(id);
	}

	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public Prenotazione savePrenotazione(@RequestBody @Validated BookingPayload body) {
		return prenotazioneService.create(body);
	}

	@PutMapping("/{id}")
	public Prenotazione findByIdAndUpdate(@PathVariable UUID id, @RequestBody BookingPayload body) {
		return prenotazioneService.findByIdAndUpdate(id, body);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void findByIdAndDelete(@PathVariable UUID id) {
		prenotazioneService.findByIdAndDelete(id);
	}
}
