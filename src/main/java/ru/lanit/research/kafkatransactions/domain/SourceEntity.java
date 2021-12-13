package ru.lanit.research.kafkatransactions.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Отправляемая сущность
 */
@Data
@Entity
@Table(name = "SOURCE_ENTITY")
public class SourceEntity {

    @Id
    private String text;

    private boolean processed;
}
