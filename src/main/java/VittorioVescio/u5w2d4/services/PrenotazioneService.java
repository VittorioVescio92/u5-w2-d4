package VittorioVescio.u5w2d4.services;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import VittorioVescio.u5w2d4.entities.Postazione;
import VittorioVescio.u5w2d4.entities.Prenotazione;
import VittorioVescio.u5w2d4.entities.User;
import VittorioVescio.u5w2d4.entities.payloads.BookingPayload;
import VittorioVescio.u5w2d4.exceptions.BadRequestException;
import VittorioVescio.u5w2d4.exceptions.NotFoundException;
import VittorioVescio.u5w2d4.repository.PrenotazioneRepository;

@Service
public class PrenotazioneService {

	@Autowired
	private PrenotazioneRepository prenotazioneRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private PostazioneService postazioneService;

	public Page<Prenotazione> find(int page, int size, String sortBy) {
		if (size < 0)
			size = 10;
		if (size > 100)
			size = 100;
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
		return prenotazioneRepository.findAll(pageable);
	}

	public Prenotazione findById(UUID id) throws NotFoundException {
		return prenotazioneRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Nessun risultato con questo id"));
	}

	public Prenotazione create(BookingPayload b) {
		Postazione postazione = postazioneService.findById(b.getPostazioneId());
		prenotazioneRepository.findByPostazioneAndDataPrenotata(postazione, b.getDataPrenotata()).ifPresent(p -> {
			throw new BadRequestException("Postazione occupata per quella data");
		});
		LocalDate twoDaysAhead = LocalDate.now().plusDays(2);
		User user = userService.findById(b.getUserId());
		if (b.getDataPrenotata().isAfter(twoDaysAhead)) {
			Prenotazione newPrenotazione = new Prenotazione(user, postazione, b.getDataPrenotata(), LocalDate.now());
			return prenotazioneRepository.save(newPrenotazione);
		} else {
			throw new BadRequestException("Devono esserci almeno due giorni prima della data di presentazione.");
		}
	}

	public Prenotazione findByIdAndUpdate(UUID id, BookingPayload p) throws NotFoundException {
		Prenotazione found = this.findById(id);
		Postazione postazione = postazioneService.findById(p.getPostazioneId());
		User user = userService.findById(p.getUserId());
		LocalDate twoDaysAhead = LocalDate.now().plusDays(2);
		if (p.getDataPrenotata().isAfter(twoDaysAhead)) {
			found.setId(id);
			found.setUser(user);
			found.setPostazione(postazione);
			found.setDataPrenotata(p.getDataPrenotata());
			return prenotazioneRepository.save(found);
		} else {
			throw new BadRequestException("Devono esserci almeno due giorni prima della data di presentazione.");
		}
	}

	public void findByIdAndDelete(UUID id) throws NotFoundException {
		Prenotazione found = this.findById(id);
		prenotazioneRepository.delete(found);
	}
}
