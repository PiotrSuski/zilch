package com.suski.zilch.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.suski.zilch.model.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {

	List<Transaction> findAllByCardId(Long cardid);
}
