package org.dcg.repository;

import org.dcg.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

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

    @Test
    public void findById() {
    }

    @Test
    public void deleteById() {
    }
}