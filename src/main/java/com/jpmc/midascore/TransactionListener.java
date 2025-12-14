package com.jpmc.midascore;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRecordRepository;
import com.jpmc.midascore.repository.UserRepository;

@Component
public class TransactionListener {

	private final UserRepository userRepository;
	private final TransactionRecordRepository transactionRecordRepository;

	private final RestTemplate restTemplate;

	public TransactionListener(UserRepository userRepository,
	                           TransactionRecordRepository transactionRecordRepository,
	                           RestTemplate restTemplate) {
	    this.userRepository = userRepository;
	    this.transactionRecordRepository = transactionRecordRepository;
	    this.restTemplate = restTemplate;
	}


	@KafkaListener(topics = "${general.kafka-topic}")
	public void listen(Transaction transaction) {

		/**
		 * Find sender
		 */
		UserRecord sender = userRepository.findById(transaction.getSenderId());
		if (sender == null)
			return;

		/**
		 * Find recipient
		 */
		UserRecord recipient = userRepository.findById(transaction.getRecipientId());
		if (recipient == null)
			return;

		/**
		 * Check balance
		 */
		if (sender.getBalance() < transaction.getAmount())
			return;

		/**
		 * Call Incentive API
		 */
		Incentive incentive = restTemplate.postForObject("http://localhost:8080/incentive", transaction,
				Incentive.class);

		float incentiveAmount = (incentive != null) ? incentive.getAmount() : 0;

		/**
		 * Update balances
		 */
		sender.setBalance(sender.getBalance() - transaction.getAmount());
		recipient.setBalance(recipient.getBalance() + transaction.getAmount() + incentiveAmount);

//        sender.setBalance(sender.getBalance() - transaction.getAmount());
//        recipient.setBalance(recipient.getBalance() + transaction.getAmount());

		userRepository.save(sender);
		userRepository.save(recipient);

		/**
		 * Save transaction record
		 */
//		TransactionRecord record = new TransactionRecord(sender, recipient, transaction.getAmount());
		
		TransactionRecord record =
		        new TransactionRecord(sender, recipient, transaction.getAmount(), incentiveAmount);

		transactionRecordRepository.save(record);
	}
}
