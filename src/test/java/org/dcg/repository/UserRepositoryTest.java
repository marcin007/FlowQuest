package org.dcg.repository;

import org.dcg.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        userRepository.deleteAll();
    }

    @Test
    public void whenFindByLogin_thenReturnUser() {
        // given
        User user = new User(1L, "login", "pass");
        userRepository.save(user);

        // when
        Optional<User> found = userRepository.findByLogin(user.getLogin());

        // then
        assertTrue(found.isPresent());
        assertEquals(found.get().getLogin(), user.getLogin());
    }
}