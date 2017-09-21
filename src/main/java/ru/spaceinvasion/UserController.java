package ru.spaceinvasion;

import static org.springframework.util.StringUtils.isEmpty;

import org.jetbrains.annotations.Contract;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Objects;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(
        path = Constants.ApiConstants.USER_API_PATH,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
public class UserController {

    private final HashMap<String, User> registeredUsers = new HashMap<>();

    @PostMapping("signin")
    public ResponseEntity<?> signIn(@RequestBody User user, HttpSession httpSession) {
        if (!checkUser(user)) {
            return ResponseEntity.badRequest()
                    .body(new RestJsonAnswer("Bad request", "Invalid username, email or password"));
        }

        final User curUser = (User) httpSession.getAttribute("user");
        if (curUser != null) {
            return ResponseEntity.badRequest().body(curUser); // Already authorized by curUser
        }

        if (!Objects.equals(registeredUsers.get(user.getUsername()), user)) {
            return ResponseEntity.badRequest()
                    .body(new RestJsonAnswer("Singning in failed", "Wrong login, password or email"));
        }
        httpSession.setAttribute("user", user);

        return ResponseEntity.ok(user);
    }

    @PostMapping("signup")
    public ResponseEntity<?> signUp(@RequestBody User user, HttpSession httpSession) {
        if (!checkUser(user)) {
            return ResponseEntity.badRequest()
                    .body(new RestJsonAnswer("Bad request", "Invalid username, email or password"));
        }

        final User curUser = (User) httpSession.getAttribute("user");
        if (curUser != null) {
            return ResponseEntity.badRequest().body(curUser); // Already authorized by curUser
        }

        if (registeredUsers.containsKey(user.getUsername())) {
            return ResponseEntity.badRequest()
                    .body(new RestJsonAnswer("Username already used", "Come up with a different username"));
        }
        httpSession.setAttribute("user", user);
        registeredUsers.put(user.getUsername(), user);

        return ResponseEntity.ok(user);
    }

    @PostMapping(path = "logout", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> logout(HttpSession httpSession) {
        if (httpSession == null || httpSession.isNew()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new RestJsonAnswer("Is not sign in yet", "You can not logout if you are not singed in"));
        }
        httpSession.invalidate();
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/{username}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getUser(@PathVariable String username) {

        final User user = registeredUsers.get(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }

    @PatchMapping
    public ResponseEntity<?> editAccount(@RequestBody User user, HttpSession httpSession) {
        if (!checkUser(user)) {
            return ResponseEntity.badRequest()
                    .body(new RestJsonAnswer("Bad request", "Invalid username, email or password"));
        }

        final User curUser = (User) httpSession.getAttribute("user");
        if (curUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new RestJsonAnswer("Unauthorized", "Sign in or sign up"));
        }

        user.setPassword(curUser.getPassword());
        httpSession.removeAttribute("user");
        httpSession.setAttribute("user", user);
        registeredUsers.remove(curUser.getUsername());
        registeredUsers.put(user.getUsername(), user);
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAccount(@RequestBody User user, HttpSession httpSession) {
        if (!checkUser(user)) {
            return ResponseEntity.badRequest()
                    .body(new RestJsonAnswer("Bad request", "Invalid username, email or password"));
        }

        final User curUser = (User) httpSession.getAttribute("user");
        if (curUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new RestJsonAnswer("Unauthorized", "Sign in or sign up"));
        }

        if (!Objects.equals(registeredUsers.get(user.getUsername()), user) || !Objects.equals(curUser, user)) {
            return ResponseEntity.badRequest()
                    .body(new RestJsonAnswer("Bad request", "Your confirmed user data is not match with origin data"));
        }

        registeredUsers.remove(user.getUsername());
        httpSession.invalidate();

        return ResponseEntity.ok().build();
    }

    @GetMapping(consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> curUser(HttpSession httpSession) {
        final User curUser = (User) httpSession.getAttribute("user");

        if (curUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new RestJsonAnswer("Unauthorized", "Sign in or sign up"));
        }

        return ResponseEntity.ok(curUser);
    }

    @Contract(value = "null -> false", pure = true)
    @SuppressWarnings("OverlyComplexBooleanExpression")
    private static boolean checkUser(User user) {
        return user != null
                && !isEmpty(user.getUsername())
                && !isEmpty(user.getPassword())
                && !isEmpty(user.getEmail());
    }
}