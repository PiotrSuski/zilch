package com.suski.zilch;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.suski.zilch.controller.CardController;
import com.suski.zilch.model.User;
import com.suski.zilch.model.ZilchCard;
import com.suski.zilch.repository.TransactionRepository;
import com.suski.zilch.repository.ZilchCardRepository;

@WebMvcTest(CardController.class)
@AutoConfigureMockMvc
public class CardControllerTest {

	@MockBean
	private ZilchCardRepository cardRepository;

	@MockBean
	private TransactionRepository transactionRepository;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testGetCards() throws Exception {
		// given
		List<ZilchCard> cards = new ArrayList<>();
		cards.add(new ZilchCard(new User("Piotr", "Suski", "Piotr.Suski@zilch.com"), "1234356"));
		Mockito.when(cardRepository.findAll()).thenReturn(cards);

		// when
		// then
		mockMvc.perform(get("/cards")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].user.firstname", is("Piotr")))
				.andExpect(jsonPath("$[0].user.lastname", is("Suski")))
				.andExpect(jsonPath("$[0].user.email", is("Piotr.Suski@zilch.com")))
				.andExpect(jsonPath("$[0].cardnumber", is("1234356")));

	}

	@Test
	public void testGetCardById() throws Exception {
		// given
		long cardId = 1;
		Optional<ZilchCard> card = Optional
				.of(new ZilchCard(new User("Piotr", "Suski", "Piotr.Suski@zilch.com"), "1234356"));
		Mockito.when(cardRepository.findById(cardId)).thenReturn(card);

		// when
		// then
		mockMvc.perform(get("/cards/1")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.user.firstname", is("Piotr")))
				.andExpect(jsonPath("$.user.lastname", is("Suski")))
				.andExpect(jsonPath("$.user.email", is("Piotr.Suski@zilch.com")))
				.andExpect(jsonPath("$.cardnumber", is("1234356")));

	}

	@Test
	public void testGetCardByIdNotExists() throws Exception {
		mockMvc.perform(get("/cards/1")).andExpect(status().isNotFound());
	}

	@Test
	public void testPostCorrectCard() throws Exception {
		// given

		String newCard = "{\"user\": {\"firstname\": \"Piotr\", \"lastname\": \"New\",\"email\": \"Piotr.New@zilch.com\" },\"cardnumber\": \"999999999999999\"}";

		mockMvc.perform(MockMvcRequestBuilders.post("/cards").content(newCard).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isCreated());
	}

	@Test
	public void testPostIncorrectEmailCard() throws Exception {
		// given

		String newCard = "{\"user\": { \"firstname\": \"Piotr\", \"lastname\": \"New\",\"email\": \"Piotr.Newzilch.com\" },\"cardnumber\": \"999999999999999\"}";

		mockMvc.perform(MockMvcRequestBuilders.post("/cards").content(newCard).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$['user.email']", is("Please provide correct email adress")));

		Mockito.verifyNoMoreInteractions(cardRepository);
	}

	@Test
	public void testPostNoFirtsNameAndIncorrectEmailCard() throws Exception {
		// given

		String newCard = "{\"user\": {\"lastname\": \"New\",\"email\": \"Piotr.Newzilch.com\" },\"cardnumber\": \"999999999999999\"}";

		mockMvc.perform(MockMvcRequestBuilders.post("/cards").content(newCard).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$['user.email']", is("Please provide correct email adress")))
				.andExpect(jsonPath("$['user.firstname']", is("Please provide user name")));

		Mockito.verifyNoMoreInteractions(cardRepository);
	}
}
