package dev.hotel.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.hotel.entite.Client;
import dev.hotel.repository.ClientRepository;

@RestController
public class ClientController {

	protected ClientRepository repo;

	/** Constructeur
	 * @param controller
	 */
	public ClientController(ClientRepository repo) {
		this.repo = repo;
	}

	// GET /clients?start=X&size=Y

	@GetMapping("clients")
	public List<Client> findAll(
			@RequestParam("start") Integer page,
			@RequestParam("size") Integer size			
			) {
		return repo.findAll(PageRequest.of(page, size)).toList();
	}

	// GET /clients/UUID

	@GetMapping("clients/{uuid}")
	public ResponseEntity<Client> findByUuid(
			@PathVariable UUID uuid		
			) {
		 Optional<Client> client = repo.findById(uuid);

		 if ( client.isEmpty() ) {
			 return ResponseEntity.status(HttpStatus.NOT_FOUND)
					 .body(client.get());
		 }

		 return ResponseEntity.status(HttpStatus.ACCEPTED)
				 .body(client.get());
	}
}