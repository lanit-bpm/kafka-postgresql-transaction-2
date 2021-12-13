package ru.lanit.research.kafkatransactions.fw;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import ru.lanit.research.kafkatransactions.domain.SourceEntity;

import javax.persistence.EntityManagerFactory;

@Configuration
@EnableConfigurationProperties(KafkaProperties.class)
@EnableKafka
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "myservice")
@Data
public class KafkaConfig {

    private int sendTransactionsFaultsNum;
    private int receiveTransactionsFaultsNum;
    private boolean businessFaults;

    @Bean
    @Primary
    public MyKafkaTransactionManager transactionManager(ProducerFactory<String, SourceEntity> myProducerFactory) {
        MyKafkaTransactionManager ktm = new MyKafkaTransactionManager(myProducerFactory, sendTransactionsFaultsNum, receiveTransactionsFaultsNum);
        ktm.setTransactionSynchronization(AbstractPlatformTransactionManager.SYNCHRONIZATION_ON_ACTUAL_TRANSACTION);
        return ktm;
    }

    @Bean
    public JpaTransactionManager jpaTransactionManager(EntityManagerFactory em) {
        return new JpaTransactionManager(em);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, SourceEntity> myListenerContainerFactory(
            ConsumerFactory<String, SourceEntity> myConsumerFactory,
            KafkaTemplate<String, SourceEntity> myKafkaTemplate,
            MyKafkaTransactionManager myKafkaTransactionManager) {

        ConcurrentKafkaListenerContainerFactory<String, SourceEntity> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(myConsumerFactory);
        factory.setReplyTemplate(myKafkaTemplate);
        factory.getContainerProperties().setTransactionManager(myKafkaTransactionManager);

        return factory;
    }

    @Bean
    public KafkaTemplate<String, SourceEntity> myKafkaTemplate(ProducerFactory<String, SourceEntity> myProducerFactory) {
        return new KafkaTemplate<>(myProducerFactory);
    }
}
