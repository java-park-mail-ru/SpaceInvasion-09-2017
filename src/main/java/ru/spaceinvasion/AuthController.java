package ru.spaceinvasion;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@RestController
@RequestMapping(path = "v1/auth")
public class AuthController {

    @RequestMapping(method = RequestMethod.POST, path = "login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> login(@RequestBody Auth auth, HttpSession httpSession) {
        String login = auth.login;
        String password = auth.password;

        Auth curAuth = (Auth)httpSession.getAttribute("auth");
        if ( curAuth != null ) {
            return ResponseEntity.badRequest().body(curAuth);
        }

        // TODO: Check if user exists in database
        httpSession.setAttribute("auth", new Auth(login, password) );

        return ResponseEntity.accepted().body("");
    }

    @RequestMapping(method = RequestMethod.POST, path = "signup", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> signUp(@RequestBody Auth auth, HttpSession httpSession) {
        String login = auth.login;
        String password = auth.password;

        Auth curAuth = (Auth)httpSession.getAttribute("auth");
        if ( curAuth != null ) {
            return ResponseEntity.badRequest().body(curAuth);
        }

        httpSession.setAttribute("auth", new Auth(login, password) );
        System.out.println(((Auth) httpSession.getAttribute("auth")).login);
        // TODO: Write new user into database

        return ResponseEntity.accepted().body("");
    }

    @RequestMapping(method = RequestMethod.POST, path = "logout", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> logout( HttpSession httpSession ) {
        httpSession.invalidate();
        return ResponseEntity.accepted().body("");
    }

    @RequestMapping(method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> curUser( HttpSession httpSession ) {
        Auth curAuth = (Auth)httpSession.getAttribute("auth");

        if (curAuth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        HashMap hashMap = new HashMap<String, String>();
        hashMap.put("login", curAuth.login);
        hashMap.put("password", curAuth.password);

        return ResponseEntity.accepted().body(hashMap);
    }
}

/*

@RequestBody RegestrationRequest
 */