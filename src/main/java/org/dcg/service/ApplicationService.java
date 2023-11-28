package org.dcg.service;

import org.dcg.dto.ApplicationDTO;
import org.dcg.entity.Application;
import org.dcg.entity.StateChangeHistory;
import org.dcg.mapper.ApplicationMapper;
import org.dcg.repository.ApplicationRepository;
import org.dcg.repository.StateChangeHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private StateChangeHistoryRepository stateChangeHistoryRepository;

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

    @Transactional
    public boolean deleteApplication(Long id, String reason) {
        Optional<Application> applicationOptional = applicationRepository.findById(id);
        if (!applicationOptional.isPresent()) {
            return false;
        }
        Application application = applicationOptional.get();
        StateChangeHistory stateChangeHistory = StateChangeHistory.builder()
                .application(application)
                .previousState(application.getState())
                .newState("DELETED")
                .changeDate(new Date())
                .reason(reason)
                .build();

        stateChangeHistoryRepository.save(stateChangeHistory);

        applicationRepository.deleteById(id);
        return true;
    }

    @Transactional
    public boolean rejectApplication(Long id, String reason) {
        Optional<Application> applicationOptional = applicationRepository.findById(id);
        if (!applicationOptional.isPresent()) {
            return false;
        }

        Application application = applicationOptional.get();
        StateChangeHistory stateChangeHistory = StateChangeHistory.builder()
                .application(application)
                .previousState(application.getState())
                .newState("REJECTED")
                .changeDate(new Date())
                .reason(reason)
                .build();

        stateChangeHistoryRepository.save(stateChangeHistory);

        Application updatedApplication = Application.builder()
                .applicationId(application.getApplicationId())
                .applicationName(application.getApplicationName())
                .applicationContent(application.getApplicationContent())
                .state("REJECTED")
                .creationDate(application.getCreationDate())
                .publicationDate(application.getPublicationDate())
                .uniqueNumber(application.getUniqueNumber())
                .build();

        applicationRepository.save(updatedApplication);
        return true;
    }

    private static boolean isUpdateAllowed(String currentState) {
        return "CREATED".equals(currentState) || "VERIFIED".equals(currentState);
    }


}
