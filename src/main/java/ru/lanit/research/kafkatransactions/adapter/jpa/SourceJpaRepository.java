package ru.lanit.research.kafkatransactions.adapter.jpa;

import org.springframework.data.repository.CrudRepository;
import ru.lanit.research.kafkatransactions.domain.SourceEntity;

public interface SourceJpaRepository extends CrudRepository<SourceEntity, Long> {
    SourceEntity findFirstByProcessedIsFalseOrderByText();
}
