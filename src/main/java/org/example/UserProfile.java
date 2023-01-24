package org.example;

public class UserProfile {
    private final String login;
    private final String password;

    UserProfile(String login, String password)
    {
        this.login = login;
        this.password = password;
    }

    public String getLogin()
    {
        return this.login;
    }

    public String getPassword()
    {
        return this.password;
    }
}
