package org.example;

public class Branch {
    private String name;
    private Commit commit;

    public Branch(String name, Commit commit) {
        this.name = name;
        this.commit = commit;
    }

    @Override
    public String toString() {
        return "{" +
                "\"name\": " + "\"" + this.name + "\"" +
                ",\"commit\": " + this.commit.toString() +
                "}";
    }
}
