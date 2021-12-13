package ru.lanit.research.kafkatransactions.adapter.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.lanit.research.kafkatransactions.app.api.MessageProducer;
import ru.lanit.research.kafkatransactions.domain.SourceEntity;

/**
 * Адаптер для отправки сообщений в Kafka
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MessageProducerImpl implements MessageProducer {

    private final KafkaTemplate<String, SourceEntity> kafkaTemplate;

    @Value("${myservice.topics.main-topic}")
    private String mainTopic;

    @Override
    public void send(SourceEntity sourceEntity) {
        if (sourceEntity != null) {

            // Отправка сущности в Kafka
            String key = sourceEntity.getText();
            kafkaTemplate.send(mainTopic, key, sourceEntity);
        }
    }

}
