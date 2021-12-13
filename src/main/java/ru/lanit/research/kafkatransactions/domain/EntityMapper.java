package ru.lanit.research.kafkatransactions.domain;

import org.mapstruct.Mapper;

/**
 * Маппер доменных сущностей
 */
@Mapper(componentModel = "spring")
public interface EntityMapper {

    TargetEntity toTargetEntity(SourceEntity sourceEntity);
}
