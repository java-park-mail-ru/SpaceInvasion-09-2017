package ru.spaceinvasion;

import org.jetbrains.annotations.Contract;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Objects;

@RestController
@RequestMapping(
        path = Constants.APIConstants.USER_API_PATH,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
public class UserController {

    private final HashMap<String, User> registeredUsers = new HashMap<>();

    @RequestMapping(method = RequestMethod.POST, path = "singin")
    public ResponseEntity<?> signIn(@RequestBody User user, HttpSession httpSession) {
        if (!checkUser(user)) {
            return ResponseEntity.badRequest()
                    .body(new RestJsonAnswer("Bad request", "Incorrect username or password"));
        }

        final User curUser = (User) httpSession.getAttribute("user");
        if (curUser != null) {
            return ResponseEntity.badRequest().body(curUser); // Already authorized by curUser
        }

        // TODO: Check if user exists in database
        if (!Objects.equals(registeredUsers.get(user.getUsername()), user)) {
            return ResponseEntity.badRequest()
                    .body(new RestJsonAnswer("Wrong login, password or email", ""));
        }
        httpSession.setAttribute("curUser", user);

        return ResponseEntity.ok(user);
    }

    @RequestMapping(method = RequestMethod.POST, path = "signup")
    public ResponseEntity<?> signUp(@RequestBody User user, HttpSession httpSession) {
        if (!checkUser(user)) {
            return ResponseEntity.badRequest()
                    .body(new RestJsonAnswer("Bad request", "Incorrect username or password"));
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
        registeredUsers.put(user.getUsername(), user); // TODO: Write new user into database

        return ResponseEntity.ok(user);
    }

    @RequestMapping(method = RequestMethod.POST, path = "logout", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> logout(HttpSession httpSession) {
        if (httpSession == null || httpSession.isNew()) {
            return ResponseEntity.badRequest()
                    .body(new RestJsonAnswer("Is not sign in yet", "You can not logout if you are not singed in"));
        }
        httpSession.invalidate();
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username) {

        final User user = registeredUsers.get(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }

    @RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity<?> editAccount(@RequestBody User user, HttpSession httpSession) {
        if (!checkUser(user)) {
            return ResponseEntity.badRequest()
                    .body(new RestJsonAnswer("Bad request", "Incorrect username or password"));
        }

        registeredUsers.replace(((User) httpSession.getAttribute("curUser")).getUsername(), user);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAccount(@RequestBody User user, HttpSession httpSession) {
        if (!checkUser(user)) {
            return ResponseEntity.badRequest()
                    .body(new RestJsonAnswer("Bad request", "Incorrect username or password"));
        }

        if (!Objects.equals(registeredUsers.get(user.getUsername()), user)) {
            return ResponseEntity.badRequest()
                    .body(new RestJsonAnswer("Bad request", "Your confirmed user data is not match with origin data"));
        }

        final User curUser = (User) httpSession.getAttribute("user");
        if (!Objects.equals(curUser, user)) {
            return ResponseEntity.badRequest()
                    .body(new RestJsonAnswer("Permission denied", "You are can not delete other user profile"));
        }

        registeredUsers.remove(user.getUsername());
        httpSession.invalidate();

        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> curUser(HttpSession httpSession) {
        final User curUser = (User) httpSession.getAttribute("user");

        if (curUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new RestJsonAnswer("Unauthorized", "Sign in or sign up"));
        }

        return ResponseEntity.ok(curUser);
    }

    @Contract(value = "null -> false", pure = true)
    private boolean checkUser(User user) {
        if (user == null) {
            return false;
        }

        final String username = user.getUsername();
        final String password = user.getPassword();
        final String email = user.getEmail();

        return username != null && password != null && email != null;
    }
}