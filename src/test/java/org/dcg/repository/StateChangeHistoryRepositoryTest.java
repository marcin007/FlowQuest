package org.dcg.repository;

import org.dcg.entity.Application;
import org.dcg.entity.StateChangeHistory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class StateChangeHistoryRepositoryTest {

    @Autowired
    private StateChangeHistoryRepository stateChangeHistoryRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private LogsRepository logsRepository;

    @Before
    public void setUp() throws Exception {
        logsRepository.deleteAll();
        stateChangeHistoryRepository.deleteAll();
        applicationRepository.deleteAll();
    }

    @Test
    public void findByApplication_ApplicationId() {
        Application application = Application.builder()
                .applicationName("Test Application")
                .creationDate(new Date())
                .state("ACTIVE")
                .build();
        Application savedApplication = applicationRepository.save(application);

        StateChangeHistory stateChangeHistory = StateChangeHistory.builder()
                .application(savedApplication)
                .previousState("NEW")
                .newState("IN_PROGRESS")
                .changeDate(new Date())
                .reason("Testing")
                .build();
        stateChangeHistoryRepository.save(stateChangeHistory);

        List<StateChangeHistory> foundHistories = stateChangeHistoryRepository.findByApplication_ApplicationId(savedApplication.getApplicationId());

        assertThat(foundHistories).isNotEmpty();
        assertThat(foundHistories.get(0).getApplication().getApplicationId()).isEqualTo(savedApplication.getApplicationId());
    }
}