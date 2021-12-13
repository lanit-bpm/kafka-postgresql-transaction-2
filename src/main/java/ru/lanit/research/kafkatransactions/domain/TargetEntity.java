package ru.lanit.research.kafkatransactions.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Полученная сущность
 */
@Data
@Entity
@Table(name = "TARGET_ENTITY")
public class TargetEntity {
    @Id
    @GeneratedValue
    private long position;

    private String text;
}
