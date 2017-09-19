package ru.spaceinvasion;

public class Auth {

    public final String login;
    public final String password;

    public Auth() {
        this.login = "";
        this.password = "";
    }

    public Auth( String login, String password ) {
        this.login = login;
        this.password = password;
    }
}
