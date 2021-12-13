package ru.lanit.research.kafkatransactions.adapter.jpa;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.lanit.research.kafkatransactions.app.api.TargetRepository;
import ru.lanit.research.kafkatransactions.domain.TargetEntity;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Адаптер для работы с БД-приемником
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TargetRepositoryImpl implements TargetRepository {

    private final TargetJpaRepository targetJpaRepository;

    public void save(TargetEntity targetEntity) {

        // Сохранение сущности в БД
        targetJpaRepository.save(targetEntity);
    }

    @Override
    public List<String> listAll() {
        // Вычитать из БД все сущности
        return targetJpaRepository.findAllByOrderByPosition().stream().map(TargetEntity::getText).map(String::trim).collect(Collectors.toList());
    }

}
