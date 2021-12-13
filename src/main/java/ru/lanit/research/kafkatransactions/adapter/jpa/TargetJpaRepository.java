package ru.lanit.research.kafkatransactions.adapter.jpa;

import org.springframework.data.repository.CrudRepository;
import ru.lanit.research.kafkatransactions.domain.TargetEntity;

import java.util.List;

public interface TargetJpaRepository extends CrudRepository<TargetEntity, Long> {
    List<TargetEntity> findAllByOrderByPosition();
}
