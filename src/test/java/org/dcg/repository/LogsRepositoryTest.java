package org.dcg.repository;

import org.dcg.entity.Application;
import org.dcg.entity.Logs;
import org.dcg.entity.User;
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


@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class LogsRepositoryTest {

    @Autowired
    private LogsRepository logsRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Before
    public void setUp() throws Exception {
        logsRepository.deleteAll();
        applicationRepository.deleteAll();
    }

    @Test
    public void findByApplicationId() {
        Application application = Application.builder()
                .applicationName("Test Application")
                .creationDate(new Date())
                .state("ACTIVE")
                .build();
        Application savedApplication = applicationRepository.save(application);

        User user = new User(1L, "login", "pass");
        Logs log = Logs.builder()
                .application(savedApplication)
                .actionDescription("Test Action")
                .dateTime(new Date())
                .user(user)
                .build();
        logsRepository.save(log);

        // Wykonaj test
        List<Logs> foundLogs = logsRepository.findByApplication_ApplicationId(savedApplication.getApplicationId());

        // Weryfikacja
        assertThat(foundLogs).isNotEmpty();
        assertThat(foundLogs.get(0).getApplication().getApplicationId()).isEqualTo(savedApplication.getApplicationId());
    }
}