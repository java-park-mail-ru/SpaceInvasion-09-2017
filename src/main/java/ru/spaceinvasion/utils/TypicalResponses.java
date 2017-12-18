package ru.spaceinvasion.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class TypicalResponses {
    public static final ResponseEntity<RestJsonAnswer> BAD_REQUEST = ResponseEntity.badRequest()
            .body(new RestJsonAnswer("Bad request", "Invalid username or password"));
    public static final ResponseEntity<RestJsonAnswer> WRONG_AUTH_DATA_RESPONSE = ResponseEntity.badRequest()
                    .body(new RestJsonAnswer("Singning in failed", "Wrong login or password"));
    public static final ResponseEntity<RestJsonAnswer> USERNAME_ALREADY_USED_RESPONSE = ResponseEntity.badRequest()
                            .body(new RestJsonAnswer("Username already used", "Come up with a different username"));
    public static final ResponseEntity<RestJsonAnswer> FORBIDDEN_RESPONSE = ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(new RestJsonAnswer("Forbidden", "Sign in or sign up"));
    public static final ResponseEntity<RestJsonAnswer> CANT_LOGOUT_IF_NOT_LOGINED_RESPONSE = ResponseEntity.status(HttpStatus.FORBIDDEN)
                                            .body(new RestJsonAnswer("Is not sign in yet", "You can not logout if you are not singed in"));
    @SuppressWarnings("unused")
    private static final ResponseEntity<RestJsonAnswer> CONFIRMATION_FAILED_RESPONSE = ResponseEntity.badRequest()
            .body(new RestJsonAnswer("Bad request", "Confirmed user data does not coincide with the original data"));
}
