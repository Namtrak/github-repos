package org.example;

public class Owner {
    private String login;

    public Owner(String login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return "{" +
                "\"login\": " + "\"" + this.login + "\"" +
                "}";
    }
}
