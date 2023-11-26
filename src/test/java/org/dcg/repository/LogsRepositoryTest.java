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
    private StateChangeHistoryRepository stateChangeHistoryRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private LogsRepository logsRepository;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        userRepository.deleteAll();
        stateChangeHistoryRepository.deleteAll();
        applicationRepository.deleteAll();
        logsRepository.deleteAll();
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
        User savedUser = userRepository.save(user);
        Logs log = Logs.builder()
                .application(savedApplication)
                .actionDescription("Test Action")
                .dateTime(new Date())
                .user(savedUser)
                .build();
        logsRepository.save(log);

        List<Logs> foundLogs = logsRepository.findByApplication_ApplicationId(savedApplication.getApplicationId());

        assertThat(foundLogs).isNotEmpty();
        assertThat(foundLogs.get(0).getApplication().getApplicationId()).isEqualTo(savedApplication.getApplicationId());
    }
}