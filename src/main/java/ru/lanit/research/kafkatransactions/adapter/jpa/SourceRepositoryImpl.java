package ru.lanit.research.kafkatransactions.adapter.jpa;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.lanit.research.kafkatransactions.app.api.SourceRepository;
import ru.lanit.research.kafkatransactions.domain.SourceEntity;

/**
 * Адаптер для работы с БД-источником
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SourceRepositoryImpl implements SourceRepository {

    private final SourceJpaRepository sourceJpaRepository;

    @Override
    public SourceEntity fetchNext() {

        // Вычитывание из БД очередной сущности, подлежащей отправке
        SourceEntity sourceEntity = sourceJpaRepository.findFirstByProcessedIsFalseOrderByText();
        if (sourceEntity != null) {

            // Обновление её статуса в БД
            sourceEntity.setProcessed(true);
            sourceJpaRepository.save(sourceEntity);
        }

        return sourceEntity;
    }

}
