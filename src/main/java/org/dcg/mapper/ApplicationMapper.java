package org.dcg.mapper;

import org.dcg.dto.ApplicationDTO;
import org.dcg.entity.Application;

public class ApplicationMapper {

    public Application toEntity(ApplicationDTO dto) {
        if (dto == null) {
            return null;
        }

        return Application.builder()
                .applicationId(dto.getApplicationId())
                .applicationName(dto.getApplicationName())
                .state(dto.getState())
                .creationDate(dto.getCreationDate())
                .build();
    }

    public ApplicationDTO toDTO(Application entity) {
        if (entity == null) {
            return null;
        }

        return ApplicationDTO.builder()
                .applicationId(entity.getApplicationId())
                .applicationName(entity.getApplicationName())
                .state(entity.getState())
                .creationDate(entity.getCreationDate())
                .build();
    }
}
