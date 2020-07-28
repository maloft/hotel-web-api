import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import dev.hotel.controller.ClientsRequests;
import dev.hotel.entite.Client;
import dev.hotel.repository.ClientsRepository;



@WebMvcTest(ClientsRequests.class)
public class ClientsRequestsTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ClientsRepository clientsRepository;

	@Test
	public void clientsPaginationTest() throws Exception {

		//List<Client> clients = clientsRepository.findAll();

//		int pageNumber = 0;
//		int pageSize = 3;
//		
//		List<Client> clientsSubsetted = clients.subList(pageNumber, pageSize-1);
		
		List<Client> clients = new ArrayList<>();
		Client client1 = new Client("Odd", "Ross");
        client1.setUuid(UUID.fromString("dcf129f1-a2f9-47dc-8265-1d844244b192"));
        clients.add(client1);
        
        Client client2 = new Client("Don", "Duck");
        client2.setUuid(UUID.fromString("f9a18170-9605-4fe6-83c8-d03a53e08bfe"));
        clients.add(client2);
        
        Client client3 = new Client("Etienne", "Joly");
        client3.setUuid(UUID.fromString("91defde0-9ad3-4e4f-886b-f5f06f601a0d"));
        clients.add(client3);
		
		
		Page<Client> paging = new PageImpl<>(clients);

		Mockito.when(clientsRepository.findAll(PageRequest.of(0, 3))).thenReturn(paging);

		mockMvc.perform(MockMvcRequestBuilders.get("/clients/pagination?start=0&size=3"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].uuid").value("dcf129f1-a2f9-47dc-8265-1d844244b192"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].nom").value("Odd"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].prenoms").value("Ross"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].uuid").value("f9a18170-9605-4fe6-83c8-d03a53e08bfe"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].nom").value("Don"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].prenoms").value("Duck"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[2].uuid").value("91defde0-9ad3-4e4f-886b-f5f06f601a0d"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[2].nom").value("Etienne"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[2].prenoms").value("Joly"));
	}

	@Test
	public void ClientFromUUIDTest_uuid_non_valide() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/clients/UUID/abc"))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andExpect(MockMvcResultMatchers.content().string("l'UUID entré n'est pas valide"));


	}





}