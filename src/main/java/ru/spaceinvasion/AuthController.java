package ru.spaceinvasion;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@RestController
@RequestMapping(path = "v1/auth", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AuthController {

    @RequestMapping(method = RequestMethod.POST, path = "login")
    public ResponseEntity<?> login(@RequestBody Auth auth, HttpSession httpSession) {
        if ( !checkAuth(auth) ) {
            return ResponseEntity.badRequest().build();
        }

        final Auth curAuth = (Auth)httpSession.getAttribute("auth");
        if ( curAuth != null ) {
            return ResponseEntity.badRequest().body(curAuth);
        }

        // TODO: Check if user exists in database
        httpSession.setAttribute("auth", auth );

        return ResponseEntity.accepted().build();
    }

    @RequestMapping(method = RequestMethod.POST, path = "signup")
    public ResponseEntity<?> signUp(@RequestBody Auth auth, HttpSession httpSession) {
        if ( !checkAuth(auth) ) {
            return ResponseEntity.badRequest().build();
        }

        final Auth curAuth = (Auth)httpSession.getAttribute("auth");
        if ( curAuth != null ) {
            return ResponseEntity.badRequest().body(curAuth);
        }

        httpSession.setAttribute("auth", auth );
        // TODO: Write new user into database

        return ResponseEntity.accepted().build();
    }

    @RequestMapping(method = RequestMethod.POST, path = "logout", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> logout( HttpSession httpSession ) {
        httpSession.invalidate();
        return ResponseEntity.accepted().body("");
    }

    @RequestMapping(method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> curUser( HttpSession httpSession ) {
        final Auth curAuth = (Auth)httpSession.getAttribute("auth");

        if (curAuth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        final HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("login", curAuth.login);
        hashMap.put("password", curAuth.password);

        return ResponseEntity.accepted().body(hashMap);
    }

    private boolean checkAuth(Auth auth) {
        if ( auth == null ) {
            return false;
        }

        final String login = auth.login;
        final String password = auth.password;

        return login != null && password != null;
    }
}

/*

@RequestBody RegestrationRequest
 */