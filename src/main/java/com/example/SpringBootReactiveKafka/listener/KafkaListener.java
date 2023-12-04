package com.example.SpringBootReactiveKafka.listener;

import com.example.SpringBootReactiveKafka.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Slf4j
@Service
public class KafkaListener {

    @Autowired
    private ReactiveKafkaConsumerTemplate<String, Employee> reactiveKafkaConsumerTemplate;

    @EventListener(ApplicationStartedEvent.class)
    public Flux<Employee> startKafkaConsumer() {
        return reactiveKafkaConsumerTemplate
                .receiveAutoAck()
                // .delayElements(Duration.ofSeconds(2L)) // BACKPRESSURE
                .doOnNext(consumerRecord -> log.info("received key={}, value={} from topic={}, offset={}",
                        consumerRecord.key(),
                        consumerRecord.value(),
                        consumerRecord.topic(),
                        consumerRecord.offset())
                )
                .map(ConsumerRecord<String, Employee>::value)
                .doOnNext(employee -> log.info("successfully consumed {}={}", Employee.class.getSimpleName(), employee))
                .doOnError(throwable -> log.error("something bad happened while consuming : {}", throwable.getMessage()));
    }


}
