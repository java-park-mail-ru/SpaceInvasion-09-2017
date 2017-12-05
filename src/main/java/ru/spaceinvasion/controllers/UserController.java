package ru.spaceinvasion.controllers;

import static org.springframework.util.StringUtils.isEmpty;

import org.jetbrains.annotations.Contract;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.spaceinvasion.utils.Constants;
import ru.spaceinvasion.utils.Exceptions;
import ru.spaceinvasion.models.User;
import ru.spaceinvasion.services.UserService;
import ru.spaceinvasion.utils.TypicalResponses;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@CrossOrigin(origins = {
        "*", // Для тестирования
                        /* Продакш машина */
        "http://space-invasion.ru",
        "https://space-invasion.ru",
        "http://www.space-invasion.ru",
        "https://www.space-invasion.ru",
                        /* Дев машина */
        "http://space-invasion-frontend.herokuapp.com",
        "https://space-invasion-frontend.herokuapp.com",
        "http://www.space-invasion-frontend.herokuapp.com",
        "https://www.space-invasion-frontend.herokuapp.com"
})
@RequestMapping(
        path = Constants.ApiConstants.USER_API_PATH,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "signin")
    public ResponseEntity<?> signIn(@RequestBody @Valid User user, HttpSession httpSession) {
        if (!checkUser(user)) {
            return TypicalResponses.BAD_REQUEST;
        }

        final Integer userId = (Integer) httpSession.getAttribute("user");
        if (userId != null) {
            User curUser = userService.getUser(userId);
            return ResponseEntity.badRequest().body(curUser); // Already authorized by curUser
        }

        if (!userService.authenticate(user)) {
            return TypicalResponses.WRONG_AUTH_DATA_RESPONSE;
        }
        User curUser = userService.getUser(user.getUsername());
        httpSession.setAttribute("user", curUser.getId());
        return ResponseEntity.ok(user);
    }

    @PostMapping(path = "signup")
    public ResponseEntity<?> signUp(@RequestBody @Valid User user, HttpSession httpSession) {
        if (!checkUser(user)) {
            return TypicalResponses.BAD_REQUEST;
        }

        final Integer userId = (Integer) httpSession.getAttribute("user");
        if (userId != null) {
            User curUser = userService.getUser(userId);
            return ResponseEntity.badRequest().body(curUser); // Already authorized by curUser
        }
        try {
            user = userService.create(user);
        } catch (DuplicateKeyException e) {
            return TypicalResponses.USERNAME_ALREADY_USED_RESPONSE;
        }
        httpSession.setAttribute("user", user.getId());

        return ResponseEntity.ok(user);
    }

    @PostMapping(path = "logout")
    public ResponseEntity<?> logout(HttpSession httpSession) {
        if (httpSession == null ||
                httpSession.getAttribute("user") == null) {
            return TypicalResponses.CANT_LOGOUT_IF_NOT_LOGINED_RESPONSE;
        }
        httpSession.invalidate();
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> getCurrentUser(HttpSession httpSession) {
        Integer userId = (Integer) httpSession.getAttribute("user");
        if (userId == null) {
            return TypicalResponses.FORBIDDEN_RESPONSE;
        }
        User curUser;
        try {
            curUser = userService.getUser(userId);
        } catch (Exceptions.NotFoundUser e) {
            return TypicalResponses.FORBIDDEN_RESPONSE;
        }
        return ResponseEntity.ok(curUser);
    }

    @GetMapping(path = "{username}")
    public ResponseEntity<?> getUser(@PathVariable String username) {
        User user;
        try {
            user = userService.getUser(username);
        } catch (Exceptions.NotFoundUser e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }

    @Contract(value = "null -> false", pure = true)
    private static boolean checkUser(User user) {
        return user != null
                && !isEmpty(user.getUsername())
                && !isEmpty(user.getPassword());
    }
}