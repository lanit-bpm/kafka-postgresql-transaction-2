package ru.lanit.research.kafkatransactions.fw;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.transaction.KafkaTransactionManager;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.DefaultTransactionStatus;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;

/**
 * Реализация KafkaTransactionManager, выбрасывающая exception'ы при коммите транзакции
 */
@Slf4j
public class MyKafkaTransactionManager extends KafkaTransactionManager {

    private final int sendTransactionsFaultsNum;
    private final int receiveTransactionsFaultsNum;

    private int sendTransactionsFaultsImitated;
    private int receiveTransactionsFaultsImitated;

    public MyKafkaTransactionManager(ProducerFactory producerFactory,
                                     int sendTransactionsFaultsNum,
                                     int receiveTransactionsFaultsNum) {
        super(producerFactory);
        this.sendTransactionsFaultsNum = sendTransactionsFaultsNum;
        this.receiveTransactionsFaultsNum = receiveTransactionsFaultsNum;
    }

    @Override
    public void doCommit(DefaultTransactionStatus status) throws TransactionException {

        String txName = TransactionSynchronizationManager.getCurrentTransactionName();
        log.trace("txName = {}", txName);
        if (txName == null) {
            if (receiveTransactionsFaultsImitated < receiveTransactionsFaultsNum) {
                receiveTransactionsFaultsImitated++;
                throw new RuntimeException("KafkaTransactionManager RECEIVING transaction fault imitated");
            }
        } else if (txName.equals("ru.lanit.research.kafkatransactions.app.impl.MainProcessor.sendNextEntity")) {
            if (sendTransactionsFaultsImitated < sendTransactionsFaultsNum) {
                sendTransactionsFaultsImitated++;
                throw new RuntimeException("KafkaTransactionManager SENDING transaction fault imitated");
            }
        } else {
            throw new IllegalStateException("Unknown transaction name");
        }

        super.doCommit(status);
    }

    @Override
    public void registerAfterCompletionWithExistingTransaction(Object transaction, List<TransactionSynchronization> synchronizations) throws TransactionException {
        super.registerAfterCompletionWithExistingTransaction(transaction, synchronizations);
    }
}
