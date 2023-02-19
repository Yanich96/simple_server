package org.example;

import jakarta.persistence.*;

@Entity
@Table(name="Account")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;
    @Column(name="login", unique = true)
    private String login;
    @Column(name="password")
    private String password;

    public UserProfile(String login, String password)
    {
        this.login = login;
        this.password = password;
    }

    public UserProfile()
    {
    }

    public String getLogin()
    {
        return this.login;
    }

    public String getPassword()
    {
        return this.password;
    }


    public boolean equals(UserProfile user)
    {
        return login.equals(user.getLogin()) && password.equals(user.getPassword());
    }

}
