package org.example;

public class Commit {
    private String sha;

    public Commit(String sha) {
        this.sha = sha;
    }

    @Override
    public String toString() {
        return "{" +
                "\"sha\": " + "\"" + this.sha + '\"' +
                "}";
    }
}
