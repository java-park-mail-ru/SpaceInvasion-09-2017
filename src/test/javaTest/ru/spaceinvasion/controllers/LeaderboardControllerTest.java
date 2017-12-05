package ru.spaceinvasion.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.spaceinvasion.models.User;
import ru.spaceinvasion.services.UserService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(print = MockMvcPrint.NONE)
public class LeaderboardControllerTest {

    @Autowired
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void clearDataBase() {
        userService.dropAll();
    }

    @Test
    public void testSignUp() throws Exception {
        mockMvc
                .perform(post("/v1/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"n02\"," +
                                "\"password\":\"soHardPassword\"," +
                                "\"email\":\"N02@ya.ru\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("username").value("n02"))
                .andExpect(jsonPath("email").value("N02@ya.ru"));
    }

    @Test
    public void testChangeScore() throws Exception {
        final MockHttpSession mockHttpSession = new MockHttpSession();
        testSignUp();
        mockMvc
                .perform(post("/v1/user/signin")
                        .session(mockHttpSession)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"n02\"," +
                                "\"password\":\"soHardPassword\"}"));
        userService.changeScore(userService.getUser((Integer)mockHttpSession.getAttribute("user")),5);
        mockMvc
                .perform(get("/v1/user")
                        .session(mockHttpSession)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("score").value(5));
        User user = new User("egorkurakov","123456","egor@live.ru");
        userService.create(user);
        userService.changeScore(user,25);
        user = new User("vasidmi","123456","vasi@dmi.ru");
        userService.create(user);
        userService.changeScore(user,20);
        user = new User("chocolateSwan","123456","chocolate@swan.ru");
        userService.create(user);
        userService.changeScore(user,24);
        mockMvc
                .perform(get("/v1/leaderboard")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("egorkurakov"))
                .andExpect(jsonPath("$[0].score").value(25))
                .andExpect(jsonPath("$[3].username").value("n02"))
                .andExpect(jsonPath("$[3].score").value(5));
    }
}

