package ru.lanit.research.kafkatransactions.app.api;

import ru.lanit.research.kafkatransactions.domain.SourceEntity;

/**
 * Компонент для работы с хранилищем отправляемых сообщений
 */
public interface SourceRepository {
    SourceEntity fetchNext();
}
