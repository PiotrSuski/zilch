package com.suski.zilch.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.suski.zilch.model.Transaction;
import com.suski.zilch.model.ZilchCard;
import com.suski.zilch.repository.TransactionRepository;
import com.suski.zilch.repository.ZilchCardRepository;

@RestController
@RequestMapping("/cards")
public class CardController {

	private ZilchCardRepository repository;

	private TransactionRepository transactionRepository;

	public CardController(ZilchCardRepository repository, TransactionRepository transactionRepository) {
		this.repository = repository;
		this.transactionRepository = transactionRepository;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Iterable<ZilchCard> getCards() {
		return repository.findAll();
	}

	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ZilchCard> read(@PathVariable long id) {
		Optional<ZilchCard> card = repository.findById(id);
		return card.isPresent() ? ResponseEntity.ok(card.get()) : ResponseEntity.notFound().build();
	}

	@GetMapping(path = "/transactions/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Iterable<Transaction> readTransactions(@PathVariable long id) {
		return transactionRepository.findAllByCardId(id);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ZilchCard addCard(@Valid @RequestBody ZilchCard card) {
		return repository.save(card);
	}

}
