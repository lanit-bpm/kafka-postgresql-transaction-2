package ru.lanit.research.kafkatransactions.app.api;

import ru.lanit.research.kafkatransactions.domain.TargetEntity;

import java.util.List;

/**
 * Компонент для работы с хранилищем полученных сообщений
 */
public interface TargetRepository {
    void save(TargetEntity targetEntity);

    List<String> listAll();
}
