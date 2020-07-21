package dev.hotel.controller;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.*;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import dev.hotel.entite.Client;
import dev.hotel.repository.ClientRepository;

public class ClientControllerTest {

	

	@Autowired
	MockMvc mockMvc;

	@MockBean
	ClientRepository repo;

	protected List<Client> clients = new ArrayList<>();

	@BeforeEach
	void setUp() {
		Client client = new Client("Odd", "Ross");
		client.setUuid(UUID.fromString("dcf129f1-a2f9-47dc-8265-1d844244b192"));
		clients.add(client);

		client = new Client("Don", "Duck");
		client.setUuid(UUID.fromString("f9a18170-9605-4fe6-83c8-d03a53e08bfe"));
		clients.add(client);

		client = new Client("Etienne", "Joly");
		client.setUuid(UUID.fromString("91defde0-9ad3-4e4f-886b-f5f06f601a0d"));
		clients.add(client);
	}

	@Test
	void findAllCLientsTest() throws Exception {

		Mockito.when(repo.findAll(PageRequest.of(0, 3))).thenReturn(new PageImpl<>(this.clients));

		mockMvc.perform(MockMvcRequestBuilders.get("/clients?start=0&size=3"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].uuid").value("dcf129f1-a2f9-47dc-8265-1d844244b192"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].uuid").value("f9a18170-9605-4fe6-83c8-d03a53e08bfe"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[2].uuid").value("91defde0-9ad3-4e4f-886b-f5f06f601a0d"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].nom").value("Odd"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].nom").value("Don"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[2].nom").value("Etienne"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].prenoms").value("Ross"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].prenoms").value("Duck"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[2].prenoms").value("Joly"));
	}

	@Test
	void findByUuidTest() throws Exception {

		Mockito.when(repo.findById(UUID.fromString("f9a18170-9605-4fe6-83c8-d03a53e08bfe")))
				.thenReturn(Optional.of(this.clients.get(1)));

		mockMvc.perform(MockMvcRequestBuilders.get("/clients/f9a18170-9605-4fe6-83c8-d03a53e08bfe"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.uuid").value("f9a18170-9605-4fe6-83c8-d03a53e08bfe"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.nom").value("Don"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.prenoms").value("Duck"));

	}
}
