package ru.lanit.research.kafkatransactions.app.api;

import ru.lanit.research.kafkatransactions.domain.SourceEntity;

/**
 * Компонент, отправляющий сообщения в Kafka
 */
public interface MessageProducer {
    void send(SourceEntity sourceEntity);
}
