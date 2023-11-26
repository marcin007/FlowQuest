package org.dcg.service;

import org.dcg.dto.ApplicationDTO;
import org.dcg.entity.Application;
import org.dcg.mapper.ApplicationMapper;
import org.dcg.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    private ApplicationMapper mapper;

    public Application createApplication(ApplicationDTO applicationDTO) {
        applicationDTO.setState("CREATED");
        Application application = mapper.toEntity(applicationDTO);
        return applicationRepository.save(application);
    }

    public Optional<Application> updateApplicationContent(Long id, String newContent) {
        return applicationRepository.findById(id)
                .map(application -> {
                    String currentState = application.getState();
                    if (isUpdateAllowed(currentState)) {
                        Application updatedApplication = Application.builder()
                                .applicationId(application.getApplicationId())
                                .applicationName(application.getApplicationName())
                                .applicationContent(newContent)
                                .state(application.getState())
                                .creationDate(application.getCreationDate())
                                .publicationDate(application.getPublicationDate())
                                .uniqueNumber(application.getUniqueNumber())
                                .build();
                        return applicationRepository.save(updatedApplication);
                    }
                    return application;
                });
    }

    private static boolean isUpdateAllowed(String currentState) {
        return "CREATED".equals(currentState) || "VERIFIED".equals(currentState);
    }
}
