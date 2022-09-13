package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {

        boolean quit = false;

        while (!quit) {
            System.out.println("Please enter GitHub username: ");
            String name = scanner.next();

            System.out.println("Type number that specifies preferred view format - 1.RAW JSON  2.Pretty JSON :");
            byte number = scanner.nextByte();

            String repositories = getRepositoriesInfo(name);

            switch (number) {
                case 1:
                    System.out.println(repositories);
                    break;
                case 2:
                    printJSONPretty(repositories);
                    break;
                default:
                    break;
            }

            System.out.println("Do you want to find another GitHub user? Please, type \'y\' or \'n\': ");
            String answer = scanner.next();

            switch (answer) {
                case "y":
                    break;
                default:
                    quit = true;
                    break;
            }
        }

        scanner.close();
    }

    public static String getRepositoriesInfo(String userName) throws IOException, URISyntaxException, InterruptedException {

        ArrayList<Repository> repositories = new ArrayList<>();
        HttpResponse<String> response = sendGetRequestToApi("https://api.github.com/users/" + userName + "/repos");

        if (response.statusCode() == 404) {
            return "{\"status\": 404, \"message\": \"Error 404. GitHub user does not exist. Please check if provided username is correct.\"}";
        }

        String getRepositories = response.body();
        JSONArray repositoriesJSONArray = new JSONArray(getRepositories);

        int i = 0;
        for (Object repository: repositoriesJSONArray) {
            JSONObject repositoryJSONObject = new JSONObject(repository.toString());

            if (repositoryJSONObject.get("fork").toString().equals("false")) {
                String repositoryName = repositoryJSONObject.get("name").toString();
                repositories.add(new Repository(repositoryName));

                String repositoryDetails = sendGetRequestToApi("https://api.github.com/repos/" + userName + "/" + repositoryName).body();
                JSONObject detailsJSONObject = new JSONObject(repositoryDetails);
                String login = detailsJSONObject.getJSONObject("owner").getString("login");
                repositories.get(i).setOwner(new Owner(login));

                String getBranches = sendGetRequestToApi("https://api.github.com/repos/" + userName + "/" + repositoryName + "/branches").body();
                JSONArray branches = new JSONArray(getBranches);

                for (Object branch: branches) {
                    JSONObject nameObject = new JSONObject(branch.toString());
                    JSONObject commitObject = new JSONObject(branch.toString()).getJSONObject("commit");
                    String name = nameObject.get("name").toString();
                    String sha = commitObject.get("sha").toString();

                    repositories.get(i).addBranch(new Branch(name, new Commit(sha)));
                }

                i++;
            }
        }

        return repositories.toString();
    }

    public static HttpResponse<String> sendGetRequestToApi(String url) throws IOException, InterruptedException, URISyntaxException {

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(new URI(url))
                .timeout(Duration.of(10, ChronoUnit.SECONDS))
                .header("Accept", "application/json")
//                .header("Authorization", "Bearer TOKEN")
                .GET()
                .build();

        return httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
    }

    public static void printJSONPretty(String repositories) {

        if (repositories.startsWith("[")) {
            JSONArray jsonArray = new JSONArray(repositories);
            System.out.println(jsonArray.toString(2));
        } else {
            JSONObject jsonObject = new JSONObject(repositories);
            System.out.println(jsonObject.toString(2));
        }
    }
}