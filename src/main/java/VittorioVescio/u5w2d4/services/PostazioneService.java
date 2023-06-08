package VittorioVescio.u5w2d4.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import VittorioVescio.u5w2d4.entities.Postazione;
import VittorioVescio.u5w2d4.exceptions.NotFoundException;
import VittorioVescio.u5w2d4.repository.PostazioneRepository;

@Service
public class PostazioneService {
	@Autowired
	private PostazioneRepository postazioneRepository;

	public Page<Postazione> find(int page, int size, String sortBy) {
		if (size < 0)
			size = 10;
		if (size > 100)
			size = 100;
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
		return postazioneRepository.findAll(pageable);
	}

	public Postazione findById(UUID id) throws NotFoundException {
		return postazioneRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Nessun risultato con questo id"));
	}

}
