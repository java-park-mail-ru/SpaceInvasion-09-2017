package ru.spaceinvasion.controllers;

import static org.springframework.util.StringUtils.isEmpty;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
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
            return ResponseEntity.ok().build();
        }

        final Integer userId = (Integer) httpSession.getAttribute("user");
        if (userId != null) {
            final User curUser = userService.getUser(userId);
            return ResponseEntity.ok(curUser); // Already authorized by curUser
        }

        if (!userService.authenticate(user)) {
            return ResponseEntity.ok().build();
        }
        final User curUser = userService.getUser(user.getUsername());
        httpSession.setAttribute("user", curUser.getId());
        return ResponseEntity.ok(user);
    }

    @PostMapping(path = "signup")
    public ResponseEntity<?> signUp(@RequestBody @Valid User user, HttpSession httpSession) {
        if (!checkUser(user)) {
            return ResponseEntity.ok().build();
        }

        final Integer userId = (Integer) httpSession.getAttribute("user");
        if (userId != null) {
            final User curUser = userService.getUser(userId);
            return ResponseEntity.ok(curUser); // Already authorized by curUser
        }
        try {
            user = userService.create(user);
        } catch (DuplicateKeyException e) {
            return ResponseEntity.ok().build();
        }
        httpSession.setAttribute("user", user.getId());

        return ResponseEntity.ok(user);
    }

    @PostMapping(path = "logout")
    public ResponseEntity<?> logout(HttpSession httpSession) {
        if (httpSession == null ||
                httpSession.getAttribute("user") == null) {
            return ResponseEntity.ok().build();
        }
        httpSession.invalidate();
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> getCurrentUser(HttpSession httpSession) {
        final Integer userId = (Integer) httpSession.getAttribute("user");
        if (userId == null) {
            return ResponseEntity.ok().build();
        }
        final User curUser;
        try {
            curUser = userService.getUser(userId);
        } catch (Exceptions.NotFoundUser e) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.ok(curUser);
    }

    @GetMapping(path = "{username_id}")
    public ResponseEntity<?> getUser(@NotNull @PathVariable Integer username_id) {
        final User user;
        try {
            user = userService.getUser(username_id);
        } catch (Exceptions.NotFoundUser e) {
            return ResponseEntity.ok().build();
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