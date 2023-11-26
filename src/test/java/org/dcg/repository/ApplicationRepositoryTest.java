package org.dcg.repository;

import org.dcg.entity.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ApplicationRepositoryTest {

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
    public void findById() {
        Application application = Application.builder()
                .applicationName("Test Application")
                .creationDate(new Date())
                .state("CREATED")
                .stateChangeHistories(Collections.emptySet())
                .build();
        Application savedApplication = applicationRepository.save(application);

        Optional<Application> foundApplication = applicationRepository.findById(savedApplication.getApplicationId());

        assertThat(foundApplication.isPresent()).isTrue();
        assertThat(foundApplication.get().getApplicationId()).isEqualTo(savedApplication.getApplicationId());
        assertThat(foundApplication.get().getApplicationName()).isEqualTo(savedApplication.getApplicationName());
        assertThat(foundApplication.get().getState()).isEqualTo(savedApplication.getState());
    }
}