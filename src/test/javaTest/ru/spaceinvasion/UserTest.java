package ru.spaceinvasion;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.spaceinvasion.models.User;

import static org.junit.Assert.assertEquals;

/**
 * Created by egor on 28.09.17.
 */


@SpringBootTest
public class UserTest {

    public User getDefaultUser() {
        return new User("UsualUser", "123", "my@email.com");
    }

    @Test
    public void userShouldHaveCorrectUsername() {
        final User user = getDefaultUser();
        assertEquals("UsualUser",user.getUsername());
    }

    @Test
    public void userShouldHaveCorrectEmail() {
        final User user = getDefaultUser();
        assertEquals("my@email.com",user.getEmail());
    }

    @Test
    public void userShouldHaveCorrectPassword() {
        final User user = getDefaultUser();
        assertEquals("123",user.getPassword());
    }

    @Test
    public void newUserShouldHaveZeroScores() {
        final User user = getDefaultUser();
        assertEquals(0,user.getScore());
    }

    @Test
    public void tryToChangeNameToUsualUser2() {
        final User user = getDefaultUser();
        user.setUsername("UsualUser2");
        assertEquals("UsualUser2",user.getUsername());
    }

    @Test
    public void tryToChangeEmail() {
        final User user = getDefaultUser();
        user.setEmail("my@email2.com");
        assertEquals("my@email2.com",user.getEmail());
    }

    @Test
    public void tryToChangePassworcTo1234() {
        final User user = getDefaultUser();
        user.setPassword("1234");
        assertEquals("1234",user.getPassword());
    }
}

