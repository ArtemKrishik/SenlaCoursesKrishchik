package com.github.krishchik.whowithme.controller.mapper;

import com.github.krishchik.whowithme.controller.dto.AbstractDto;
import com.github.krishchik.whowithme.model.AbstractEntity;

public interface IGenericMapper <E extends AbstractEntity, D extends AbstractDto>{
    E toEntity(D dto);

    D toDto(E entity);
}
