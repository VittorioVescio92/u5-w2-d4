package VittorioVescio.u5w2d4.entities.payloads;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class BookingPayload {
	@NotNull(message = "userId obbligatorio!")
	UUID userId;
	@NotNull(message = "postazioneId obbligatorio!")
	UUID postazioneId;
	@NotNull(message = "dataPrenotata obbligatoria!")
	LocalDate dataPrenotata;

	LocalDate dataPrenotazione = LocalDate.now();
}
