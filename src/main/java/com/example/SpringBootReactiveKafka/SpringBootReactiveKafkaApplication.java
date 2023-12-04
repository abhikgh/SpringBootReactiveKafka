package com.example.SpringBootReactiveKafka;

import com.example.SpringBootReactiveKafka.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;

import java.util.UUID;

@SpringBootApplication
@Slf4j
public class SpringBootReactiveKafkaApplication implements CommandLineRunner {

	@Autowired
	private ReactiveKafkaProducerTemplate<String, Employee> reactiveKafkaProducerTemplate;

	@Value("${kafka.topic}")
	private String topic;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootReactiveKafkaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Employee employee = new Employee("djdjd", "thfhfjf");
		reactiveKafkaProducerTemplate.send(topic,UUID.randomUUID().toString(), employee)
				.doOnSuccess(senderResult -> log.info("sent {} offset : {}", employee, senderResult.recordMetadata().offset()))
				.subscribe();

	}
}
