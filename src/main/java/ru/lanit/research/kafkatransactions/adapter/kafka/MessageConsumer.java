package ru.lanit.research.kafkatransactions.adapter.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.lanit.research.kafkatransactions.app.api.ReceiverService;
import ru.lanit.research.kafkatransactions.domain.SourceEntity;

/**
 * Адаптер, получающий сообщения из Kafka и вызывающий их обработку в ядре приложения
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MessageConsumer {

    private final ReceiverService receiverService;

    @KafkaListener(
            topics = "${myservice.topics.main-topic}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "myListenerContainerFactory"
    )
    @Transactional(transactionManager = "jpaTransactionManager", propagation = Propagation.REQUIRED)
    // транзакционный метод, получающий сообщения из Kafka
    public void receiveEntity(SourceEntity sourceEntity) {
        receiverService.receiveEntity(sourceEntity);
    }
}
