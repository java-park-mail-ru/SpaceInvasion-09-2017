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

        final User curUser = (User) httpSession.getAttribute("user");
        if (curUser != null) {
            return ResponseEntity.badRequest().body(curUser); // Already authorized by curUser
        }

        if (!userService.validate(user)) {
            return TypicalResponses.WRONG_AUTH_DATA_RESPONSE;
        }
        httpSession.setAttribute("user", user);
        return ResponseEntity.ok(user);
    }

    @PostMapping(path = "signup")
    public ResponseEntity<?> signUp(@RequestBody @Valid User user, HttpSession httpSession) {
        if (!checkUser(user)) {
            return TypicalResponses.BAD_REQUEST;
        }

        final User curUser = (User) httpSession.getAttribute("user");
        if (curUser != null) {
            return ResponseEntity.badRequest().body(curUser); // Already authorized by curUser
        }
        try {
            user = userService.create(user);
        } catch (DuplicateKeyException e) {
            return TypicalResponses.USERNAME_ALREADY_USED_RESPONSE;
        }
        httpSession.setAttribute("user", user);

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
        User curUser = (User) httpSession.getAttribute("user");
        if (curUser == null) {
            return TypicalResponses.FORBIDDEN_RESPONSE;
        }
        try {
            curUser = userService.getUser(curUser.getId());
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

    @PatchMapping
    public ResponseEntity<?> editAccount(@RequestBody @Valid User user, HttpSession httpSession) {
        if (!checkUser(user)) {
            return TypicalResponses.BAD_REQUEST;
        }

        final User curUser = (User) httpSession.getAttribute("user");
        if (curUser == null) {
            return TypicalResponses.UNAUTHORIZED_RESPONSE;
        }

        try {
            user = userService.update(curUser, user.getUsername(),
                    user.getEmail(),user.getPassword());
        } catch (DuplicateKeyException e) {
            return TypicalResponses.USERNAME_ALREADY_USED_RESPONSE;
            //TODO: Maybe email?
        }

        httpSession.removeAttribute("user");
        httpSession.setAttribute("user", user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAccount(@RequestBody @Valid User user, HttpSession httpSession) {
        if (!checkUser(user)) {
            return TypicalResponses.BAD_REQUEST;
        }

        final User curUser = (User) httpSession.getAttribute("user");
        if (curUser == null) {
            return TypicalResponses.FORBIDDEN_RESPONSE;
        }
        try {
            userService.delete(user);
        } catch (Exceptions.NotFoundUser e) {
            return TypicalResponses.BAD_REQUEST;
        }
        httpSession.invalidate();

        return ResponseEntity.ok(null);
    }

    @Contract(value = "null -> false", pure = true)
    private static boolean checkUser(User user) {
        return user != null
                && !isEmpty(user.getUsername())
                && !isEmpty(user.getPassword());
    }
}