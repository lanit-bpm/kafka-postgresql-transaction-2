package ru.lanit.research.kafkatransactions.fw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "ru.lanit")
@EnableJpaRepositories("ru.lanit.research.kafkatransactions.adapter.jpa")
@EntityScan("ru.lanit.research.kafkatransactions.domain")
@EnableTransactionManagement
@EnableScheduling
public class Application {

    public static void main(String[] args) {
        var application = Application.createSpringApplication();
        application.addInitializers(new TestContainersInitializer.Initializer());
        application.run(args);
    }

    public static SpringApplication createSpringApplication() {
        return new SpringApplication(Application.class);
    }
}
