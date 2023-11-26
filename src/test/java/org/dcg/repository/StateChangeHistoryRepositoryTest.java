//package org.dcg.repository;
//
//import org.dcg.entity.Application;
//import org.dcg.entity.StateChangeHistory;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.Date;
//import java.util.List;
//
//import static org.junit.Assert.*;
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@ActiveProfiles("test")
//public class StateChangeHistoryRepositoryTest {
//
//    @Autowired
//    private StateChangeHistoryRepository stateChangeHistoryRepository;
//
//    @Autowired
//    private ApplicationRepository applicationRepository;
//
//    @Test
//    public void findByApplicationId() {
//        Application application = new Application();
//        application.setApplicationName("Test Application");
//        application.setCreationDate(new Date());
//        application.setState("CREATED");
//        Application savedApplication = applicationRepository.save(application);
//
//        StateChangeHistory history = new StateChangeHistory();
//        history.setApplication(savedApplication);
//        history.setChangeDate(new Date());
//        history.setPreviousState("CREATED");
//        history.setNewState("PROCESSED");
//        stateChangeHistoryRepository.save(history);
//
//        // Wykonaj test
//        List<StateChangeHistory> foundHistories = stateChangeHistoryRepository.findByApplicationId(savedApplication.getApplicationId());
//
//        // Weryfikacja
//        assertThat(foundHistories).isNotEmpty();
//        assertThat(foundHistories.get(0).getApplication()).isEqualTo(savedApplication);
//    }
//}