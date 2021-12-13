package ru.lanit.research.kafkatransactions.app.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.lanit.research.kafkatransactions.app.api.ReceiverService;
import ru.lanit.research.kafkatransactions.app.api.TargetRepository;
import ru.lanit.research.kafkatransactions.domain.EntityMapper;
import ru.lanit.research.kafkatransactions.domain.SourceEntity;
import ru.lanit.research.kafkatransactions.domain.TargetEntity;
import ru.lanit.research.kafkatransactions.fw.KafkaConfig;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Компонент, сохраняющий сообщения в хранилище-получателе
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ReceiverServiceImpl implements ReceiverService {

    private final TargetRepository targetRepository;
    private final EntityMapper entityMapper;
    private final KafkaConfig kafkaConfig;

    private List<Integer> imitatedFaults = new ArrayList<>(); // имитированные сбои

    public void receiveEntity(SourceEntity sourceEntity) {

        // Мапинг сообщения в сущность
        TargetEntity targetEntity = entityMapper.toTargetEntity(sourceEntity);
        log.info("Received: {}", targetEntity.getText());

        imitateFault(1);

        // Сохранение сущности в хранилище
        targetRepository.save(targetEntity);

        imitateFault(2);

        // Показать все сущности в хранилище
        List<String> list = targetRepository.listAll();
        log.info("List of received entities: {}", list);
    }

    private void imitateFault(int faultId) {
        if (kafkaConfig.isBusinessFaults() && !imitatedFaults.contains(faultId)) {
            imitatedFaults.add(faultId);
            throw new IllegalStateException(MessageFormat.format("Receiver fault {0}", faultId));
        }
    }

}
