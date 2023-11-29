package org.dcg.service;

import org.dcg.dto.ApplicationDTO;
import org.dcg.entity.Application;
import org.dcg.entity.StateChangeHistory;
import org.dcg.entity.Status;
import org.dcg.mapper.ApplicationMapper;
import org.dcg.repository.ApplicationRepository;
import org.dcg.repository.StateChangeHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Transactional(readOnly = true)
    public Page<Application> getApplications(String name, String state, Pageable pageable) {
        return applicationRepository.findByApplicationNameContainingAndState(name, state, pageable);
    }

    public Application createApplication(ApplicationDTO applicationDTO) {
        Application application = mapper.toEntity(applicationDTO);
        return applicationRepository.save(application);
    }

    @Transactional
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
        StateChangeHistory stateChangeHistory = buildStateChangeHistory(reason, Status.DELETED, application);
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
        StateChangeHistory stateChangeHistory = buildStateChangeHistory(reason, Status.REJECTED, application);

        stateChangeHistoryRepository.save(stateChangeHistory);

        Application updatedApplication = Application.builder()
                .applicationId(application.getApplicationId())
                .applicationName(application.getApplicationName())
                .applicationContent(application.getApplicationContent())
                .state(Status.REJECTED.name())
                .creationDate(application.getCreationDate())
                .publicationDate(application.getPublicationDate())
                .uniqueNumber(application.getUniqueNumber())
                .build();

        applicationRepository.save(updatedApplication);
        return true;
    }

    private static StateChangeHistory buildStateChangeHistory(String reason, Status state, Application application) {
        return StateChangeHistory.builder()
                .application(application)
                .previousState(application.getState())
                .newState(state.name())
                .changeDate(new Date())
                .reason(reason)
                .build();
    }

    private static boolean isUpdateAllowed(String currentState) {
        return Status.CREATED.name().equals(currentState) || Status.VERIFIED.name().equals(currentState);
    }
}
