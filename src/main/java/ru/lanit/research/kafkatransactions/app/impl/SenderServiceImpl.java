package ru.lanit.research.kafkatransactions.app.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.lanit.research.kafkatransactions.app.api.MessageProducer;
import ru.lanit.research.kafkatransactions.app.api.SenderService;
import ru.lanit.research.kafkatransactions.app.api.SourceRepository;
import ru.lanit.research.kafkatransactions.domain.SourceEntity;
import ru.lanit.research.kafkatransactions.fw.KafkaConfig;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Компонент, отправляющий сообщения из хранилища-источника в Kafka
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SenderServiceImpl implements SenderService {

    private final SourceRepository sourceRepository;
    private final MessageProducer messageProducer;
    private final KafkaConfig kafkaConfig;

    private List<Integer> imitatedFaults = new ArrayList<>(); // имитированные сбои

    @Override
    @Transactional(transactionManager = "jpaTransactionManager",
            propagation = Propagation.REQUIRED) // начинает транзакцию JPA
    public void sendNextEntity() {
        imitateFault(1);

        SourceEntity sourceEntity = sourceRepository.fetchNext();
        if (sourceEntity != null) {
            log.info("Sending: {}", sourceEntity);

            imitateFault(2);

            // Отправка сущности в Kafka
            messageProducer.send(sourceEntity);

            imitateFault(3);

        } else {
            log.info("No entities left to send");
        }
    }

    private void imitateFault(int faultId) {
        if (kafkaConfig.isBusinessFaults() && !imitatedFaults.contains(faultId)) {
            imitatedFaults.add(faultId);
            throw new IllegalStateException(MessageFormat.format("Sender fault {0}", faultId));
        }
    }
}
