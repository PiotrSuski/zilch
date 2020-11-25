package com.suski.zilch;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.suski.zilch.model.User;
import com.suski.zilch.model.ZilchCard;
import com.suski.zilch.repository.ZilchCardRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ZilchApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private ZilchCardRepository cardRepository;

	@Test
	void contextLoads() {
	}
	
	@Test
	public void getCardById() throws Exception {
		
		//when
		ZilchCard card = this.restTemplate.getForObject("http://localhost:" + port + "/cards/2",	ZilchCard.class);
		
		//then
		assertThat(card).isNotNull();
		assertThat(card.getCardnumber()).isEqualTo("122298709799");
		assertThat(card.getUser()).isNotNull();
		assertThat(card.getUser().getFirstname()).isEqualTo("Piotr");
		assertThat(card.getUser().getLastname()).isEqualTo("Suski");
		assertThat(card.getUser().getEmail()).isEqualTo("Piotr.Suski@zilch.com");

	}
	
	@Test
	void addNewUser() throws Exception {
		//given
		ZilchCard card = new ZilchCard(new User("Piotr", "New", "Piotr.New@zilch.com"), "12343568899");

		//when
		//then
		mockMvc.perform(
				post("/cards").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(card)))
				.andExpect(MockMvcResultMatchers.status().isCreated());

		Optional<ZilchCard> findCard = cardRepository.findById(3l);

		assertThat(findCard.isPresent()).isTrue();
		assertThat(findCard.get()).isNotNull();

		assertThat(findCard.get().getCardnumber()).isEqualTo("12343568899");
		assertThat(findCard.get().getUser()).isNotNull();
		assertThat(findCard.get().getUser().getFirstname()).isEqualTo("Piotr");
		assertThat(findCard.get().getUser().getLastname()).isEqualTo("New");
		assertThat(findCard.get().getUser().getEmail()).isEqualTo("Piotr.New@zilch.com");

	}

}
