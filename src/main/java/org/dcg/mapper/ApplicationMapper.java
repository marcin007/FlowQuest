package org.dcg.mapper;

import org.dcg.dto.ApplicationDTO;
import org.dcg.entity.Application;
import org.dcg.entity.Status;

import java.util.Date;

public class ApplicationMapper {

    public Application toEntity(ApplicationDTO dto) {
        if (dto == null) {
            return null;
        }

        return Application.builder()
                .applicationName(dto.getApplicationName())
                .state(Status.CREATED.name())
                .creationDate(new Date())
                .build();
    }
}
