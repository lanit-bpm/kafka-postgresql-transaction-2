package ru.lanit.research.kafkatransactions.app.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.lanit.research.kafkatransactions.app.api.SenderService;

/**
 * Оркестратор отправки сообщений
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class MainProcessor {

    private final SenderService senderService;

    @Scheduled(fixedDelay = 3000)
    @Transactional // начинает транзакцию Kafka (default transaction manager)
    public void sendNextEntity() {
        // Вычитать очередную сущность из БД и отправить ее в Kafka
        senderService.sendNextEntity();
    }

}
