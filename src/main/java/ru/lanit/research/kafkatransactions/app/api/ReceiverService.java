package ru.lanit.research.kafkatransactions.app.api;

import ru.lanit.research.kafkatransactions.domain.SourceEntity;

/**
 * Компонент, сохраняющий сообщения в хранилище-получателе
 */
public interface ReceiverService {

    void receiveEntity(SourceEntity sourceEntity);

}
