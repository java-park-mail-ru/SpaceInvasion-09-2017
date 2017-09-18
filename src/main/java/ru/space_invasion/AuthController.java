package ru.space_invasion;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RequestMapping("/v1/auth")
@RestController
public class AuthController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(method = RequestMethod.GET, path = "/login")
    public Auth authLogin(HttpSession httpSession) {
        httpSession.getId();
        return new Auth();
    }

}

/*

@RequestBody RegestrationRequest
 */