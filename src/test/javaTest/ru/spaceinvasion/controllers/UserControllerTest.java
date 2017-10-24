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

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by egor on 28.09.17.
 */

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(print = MockMvcPrint.NONE)
public class UserControllerTest {

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
    public void testMe() throws Exception {
        mockMvc
                .perform(get("/v1/user/me"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testSignIn() throws Exception {
        testSignUp();
        final MockHttpSession mockHttpSession = new MockHttpSession();
        mockMvc
                .perform(post("/v1/user/signin")
                        .session(mockHttpSession)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"n02\"," +
                                "\"password\":\"soHardPassword\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("username").value("n02"));
        assertEquals(((User)mockHttpSession.getAttribute("user"))
                        .getUsername(), "n02");
    }

    @Test
    public void testLogout() throws Exception {
        final MockHttpSession mockHttpSession = new MockHttpSession();
        testSignUp();
        mockMvc
                .perform(post("/v1/user/signin")
                        .session(mockHttpSession)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"n02\"," +
                                "\"password\":\"soHardPassword\"}"));
        mockMvc
                .perform(post("/v1/user/logout")
                        .session(mockHttpSession))
                .andExpect(status().isOk());
    }
}

