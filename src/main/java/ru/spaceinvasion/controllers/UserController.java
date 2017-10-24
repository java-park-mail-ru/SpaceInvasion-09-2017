package ru.spaceinvasion.controllers;

import static org.springframework.util.StringUtils.isEmpty;

import org.jetbrains.annotations.Contract;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.spaceinvasion.utils.Constants;
import ru.spaceinvasion.utils.Exceptions;
import ru.spaceinvasion.models.User;
import ru.spaceinvasion.services.UserService;
import ru.spaceinvasion.utils.RestJsonAnswer;

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

    // Typical requests
    private static final ResponseEntity<RestJsonAnswer> BAD_REQUEST = ResponseEntity.badRequest()
            .body(new RestJsonAnswer("Bad request", "Invalid username or password"));
    private static final ResponseEntity<RestJsonAnswer> WRONG_AUTH_DATA_RESPONSE = ResponseEntity.badRequest()
            .body(new RestJsonAnswer("Singning in failed", "Wrong login or password"));
    private static final ResponseEntity<RestJsonAnswer> USERNAME_ALREADY_USED_RESPONSE = ResponseEntity.badRequest()
            .body(new RestJsonAnswer("Username already used", "Come up with a different username"));
    private static final ResponseEntity<RestJsonAnswer> UNAUTHORIZED_RESPONSE = ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new RestJsonAnswer("Unauthorized", "Sign in or sign up"));
    private static final ResponseEntity<RestJsonAnswer> CANT_LOGOUT_IF_LOGINED_RESPONE = ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new RestJsonAnswer("Is not sign in yet", "You can not logout if you are not singed in"));

    @SuppressWarnings("unused")
    private static final ResponseEntity<RestJsonAnswer> CONFIRMATION_FAILED_RESPONSE = ResponseEntity.badRequest()
            .body(new RestJsonAnswer("Bad request", "Your confirmed user data is not match with origin data"));

    @PostMapping(path = "signin", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> signIn(@RequestBody @Valid User user, HttpSession httpSession) {
        if (!checkUser(user)) {
            return BAD_REQUEST;
        }

        final User curUser = (User) httpSession.getAttribute("user");
        if (curUser != null) {
            return ResponseEntity.badRequest().body(curUser); // Already authorized by curUser
        }

        if (!userService.validate(user)) {
            return WRONG_AUTH_DATA_RESPONSE;
        }
        httpSession.setAttribute("user", user);
        return ResponseEntity.ok(user);
    }

    @PostMapping(path = "signup", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> signUp(@RequestBody @Valid User user, HttpSession httpSession) {
        if (!checkUser(user)) {
            return BAD_REQUEST;
        }

        final User curUser = (User) httpSession.getAttribute("user");
        if (curUser != null) {
            return ResponseEntity.badRequest().body(curUser); // Already authorized by curUser
        }
        try {
            user = userService.create(user);
        } catch (DuplicateKeyException e) {
            return USERNAME_ALREADY_USED_RESPONSE;
        }
        httpSession.setAttribute("user", user);

        return ResponseEntity.ok(user);
    }

    @PostMapping(path = "logout", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> logout(HttpSession httpSession) {
        if (httpSession == null ||
                httpSession.getAttribute("user") == null) {
            return CANT_LOGOUT_IF_LOGINED_RESPONE;
        }
        httpSession.invalidate();
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "me", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getCurrentUser(HttpSession httpSession) {
        User curUser = (User) httpSession.getAttribute("user");
        if (curUser == null) {
            return UNAUTHORIZED_RESPONSE;
        }
        curUser = userService.getUser(curUser);
        return ResponseEntity.ok(curUser);
    }

    @GetMapping(path = "{username}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getUser(@PathVariable String username) {

        User user = new User();
        user.setUsername(username);
        try {
            user = userService.getUser(user);
        } catch (Exceptions.NotFoundUser e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }

    @PatchMapping
    public ResponseEntity<?> editAccount(@RequestBody @Valid User user, HttpSession httpSession) {
        if (!checkUser(user)) {
            return BAD_REQUEST;
        }

        final User curUser = (User) httpSession.getAttribute("user");
        if (curUser == null) {
            return UNAUTHORIZED_RESPONSE;
        }

        try {
            user = userService.update(curUser, user.getUsername(),
                    user.getEmail(),user.getPassword());
        } catch (DuplicateKeyException e) {
            return USERNAME_ALREADY_USED_RESPONSE;
            //TODO: Maybe email?
        }

        httpSession.removeAttribute("user");
        httpSession.setAttribute("user", user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAccount(@RequestBody @Valid User user, HttpSession httpSession) {
        if (!checkUser(user)) {
            return BAD_REQUEST;
        }

        final User curUser = (User) httpSession.getAttribute("user");
        if (curUser == null) {
            return UNAUTHORIZED_RESPONSE;
        }
        try {
            userService.delete(user);
        } catch (Exceptions.NotFoundUser e) {
            return BAD_REQUEST;
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