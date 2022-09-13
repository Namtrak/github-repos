package org.example;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    private String name;
    private Owner owner;
    private List<Branch> branches;

    public Repository(String name) {
        this.name = name;
        this.branches = new ArrayList<>();
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public void addBranch(Branch branch) {
        this.branches.add(branch);
    }

    @Override
    public String toString() {
        return "{" +
                "\"name\": " + "\"" + this.name + '\"' +
                ",\"owner\": " + this.owner.toString() +
                ",\"branches\": " + this.branches.toString() +
                "}";
    }
}
