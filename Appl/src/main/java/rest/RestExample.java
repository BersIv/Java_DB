package rest;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Pattern;

public class RestExample {
    public static String token;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("To control db use commands:\n GET\n POST\n DELETE\n AUTH\n EXIT\n" +
                "enter help commandName to see examples");
        while (true) {
            System.out.print("Enter command to execute: ");
            String command = sc.nextLine().toLowerCase(Locale.ROOT);
            try {
                commandParser(command);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                //errorParser(e.getMessage());
            }
        }

    }

    public static void commandParser(String command) {
        Scanner sc = new Scanner(command);
        switch (sc.next()) {
            case ("get") -> {
                getParser(sc.nextLine());
            }
            case ("post") -> {
                postParser(sc.nextLine());
            }
            case ("delete") ->{
                deleteParser(sc.nextLine());
            }
            case ("auth") -> {
                authParser(sc.nextLine());
            }
            case ("help") -> {
                helpParser(sc.nextLine());
            }
            case ("exit") -> {
                System.exit(1);

            }
            default -> {
                System.out.println("Incorrect command\n");
            }
        }
    }

    public static void authParser(String command) {
        Scanner sc = new Scanner(command);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String url = "http://localhost:8080/auth/signIn";
        HttpEntity<String> request = new HttpEntity<>("{\n" +
                "    \"userName\":\"" + sc.next() + "\",\n" +
                "    \"password\":\"" + sc.next() +"\"\n" +
                "}", headers);
        responseEntity = restTemplate.postForEntity(url, request, String.class);
        int indBeg = responseEntity.getBody().indexOf("token");
        int indEnd = responseEntity.getBody().indexOf("\"}");
        token = responseEntity.getBody();
        token = token.substring(indBeg + 8, indEnd);
        stringParser(responseEntity.getBody());
        token = "Bearer " + token;
    }

    public static void getParser(String command) {
        Scanner sc = new Scanner(command);
        RestTemplate restTemplate = new RestTemplate();
        switch (sc.next()) {
            case ("operations"), ("operation") -> {
                ResponseEntity<String> responseEntity;
                if (sc.hasNextInt()) {
                    String url = "http://localhost:8080/operation/getById-";
                    responseEntity = restTemplate.getForEntity(url + sc.nextInt(), String.class);
                } else if (sc.hasNext()) {
                    String line = sc.next();
                    if (line.equals("more")){
                        String url = "http://localhost:8080/operation/getMoreThan-";
                        responseEntity = restTemplate.getForEntity(url + sc.nextInt(), String.class);
                    } else if (line.equals("less")){
                        String url = "http://localhost:8080/operation/getLessThan-";
                        responseEntity = restTemplate.getForEntity(url + sc.nextInt(), String.class);
                    } else {
                        String url = "http://localhost:8080/operation/getByName-";
                        responseEntity = restTemplate.getForEntity(url + line, String.class);
                    }
                } else {
                    String url = "http://localhost:8080/operation/get";
                    responseEntity = restTemplate.getForEntity(url, String.class);
                }
                stringParser(responseEntity.getBody());
            }
            case ("articles"), ("article") -> {
                ResponseEntity<String> responseEntity;
                if (sc.hasNextInt()) {
                    String url = "http://localhost:8080/article/getById-";
                    responseEntity = restTemplate.getForEntity(url + sc.nextInt(), String.class);
                } else if (sc.hasNext()) {
                    String url = "http://localhost:8080/article/getByName-";
                    responseEntity = restTemplate.getForEntity(url + sc.next(), String.class);
                } else {
                    String url = "http://localhost:8080/article/get";
                    responseEntity = restTemplate.getForEntity(url, String.class);
                }
                stringParser(responseEntity.getBody());

            }
            case ("balance") -> {
                ResponseEntity<String> responseEntity;
                if (sc.hasNextInt()) {
                    String url = "http://localhost:8080/balance/getById-";
                    responseEntity = restTemplate.getForEntity(url + sc.nextInt(), String.class);
                } else if (sc.hasNext()) {
                    System.out.println("Bad command");
                    break;
                } else {
                    String url = "http://localhost:8080/balance/get";
                    responseEntity = restTemplate.getForEntity(url, String.class);
                }
                stringParser(responseEntity.getBody());
            }
            default -> {
                System.out.println("No such table");
            }
        }
    }

    public static void postParser(String command) {
        Scanner sc = new Scanner(command);
        RestTemplate restTemplate = new RestTemplate();
        switch (sc.next()) {
            case ("operations"), ("operation") -> {
                ResponseEntity<String> responseEntity;
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.set("Authorization", token);
                if(!sc.hasNext()){
                    System.out.println("Bad operation input");
                    break;
                }
                String line = sc.nextLine();
                if(!Pattern.matches(" *[0-9]+ *[0-9]+ *", line)){
                    System.out.println("Bad operation input");
                    break;
                }
                sc = new Scanner(line);
                String url = "http://localhost:8080/operation/add-" + sc.next() + "-" + sc.next();
                HttpEntity<String> request = new HttpEntity<>(headers);
                responseEntity = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
                //responseEntity = restTemplate.postForEntity(url, request, String.class);
                stringParser(responseEntity.getBody());
            }
            case ("article"), ("articles") -> {
                if (sc.hasNextInt()) {
                    System.out.println("Name of article can't be a number");
                    break;
                }
                ResponseEntity<String> responseEntity;
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.set("Authorization", token);
                String url = "http://localhost:8080/article/add";
                HttpEntity<String> request = new HttpEntity<>("{\n" +
                        "    \"name\":\"" + sc.next() + "\"\n" +
                        "}", headers);
                responseEntity = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
                //responseEntity = restTemplate.postForEntity(url, request, String.class);
                stringParser(responseEntity.getBody());
            }
            case ("balance") -> {
                //доработать ввод данных
                ResponseEntity<String> responseEntity;
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.set("Authorization", token);
                String url = "http://localhost:8080/balance/add";
                String line = sc.nextLine();
                if(!Pattern.matches(" *[0-9]+ [0-9]+", line)){
                    System.out.println("Bad balance input");
                    break;
                }
                sc = new Scanner(line);
                HttpEntity<String> request = new HttpEntity<>("{\n" +
                        "    \"debit\":" + sc.next() + ",\n" +
                        "    \"credit\":" + sc.next() + "\n" +
                        "}", headers);
                //responseEntity = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
                responseEntity = restTemplate.postForEntity(url, request, String.class);
                stringParser(responseEntity.getBody());
            }
            default -> {
                System.out.println("No such table");
            }
        }
    }

    public static void deleteParser(String command){
        Scanner sc = new Scanner(command);
        RestTemplate restTemplate = new RestTemplate();
        switch (sc.next()) {
            case ("operations"), ("operation") -> {
                ResponseEntity<String> responseEntity;
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.set("Authorization", token);
                if(sc.hasNextInt()){
                    String url = "http://localhost:8080/operation/deleteById-" + sc.next();
                    HttpEntity<String> request = new HttpEntity<>(headers);
                    //restTemplate.delete(url, headers, request, String.class);
                    responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);
                    stringParser(responseEntity.getBody());
                } else if (sc.hasNext()){
                    String url = "http://localhost:8080/operation/deleteByName-" + sc.next();
                    HttpEntity<String> request = new HttpEntity<>(headers);
                    responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);
                    stringParser(responseEntity.getBody());
                } else {
                    System.out.println("Incorrect delete operation arguments");
                }
                //responseEntity = restTemplate.postForEntity(url, request, String.class);
                //stringParser(responseEntity.getBody());
            }
            case ("article"), ("articles") -> {
                ResponseEntity<String> responseEntity;
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.set("Authorization", token);
                if(sc.hasNextInt()){
                    String url = "http://localhost:8080/article/deleteById-" + sc.next();
                    HttpEntity<String> request = new HttpEntity<>(headers);
                    responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);
                    stringParser(responseEntity.getBody());
                } else {
                    System.out.println("Incorrect delete operation arguments");
                }
            }
            case ("balance") ->{
                ResponseEntity<String> responseEntity;
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.set("Authorization", token);
                if(sc.hasNextInt()){
                    String url = "http://localhost:8080/balance/deleteById-" + sc.next();
                    HttpEntity<String> request = new HttpEntity<>(headers);
                    responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);
                    stringParser(responseEntity.getBody());
                } else {
                    System.out.println("Incorrect delete operation arguments");
                }
            }
        }

    }

    public static void helpParser(String command){
        Scanner sc = new Scanner(command);
        RestTemplate restTemplate = new RestTemplate();
        switch (sc.next()) {
            case ("get") -> {
                System.out.println("get operation - to get operation list");
            }
            case ("post") -> {
                System.out.println("post operation articleId balanceId - to add new operation");
            }
            case ("delete") -> {
                System.out.println("delete operation operationId - to delete operation");
            }
            case ("auth") -> {
                System.out.println("auth userName password - to authenticate");
            }
            default -> {
                System.out.println("Bad command name");
            }
        }
    }

    public static void stringParser(String string) {
        Scanner sc = new Scanner(string);
        sc.skip("\\[|\\{|");
        sc.useDelimiter("},\\{|\\]");
        while (sc.hasNext()) {
            System.out.println(sc.next());
        }
    }

    public static void errorParser(String string) {
        int ind = string.indexOf(",\"path");
        System.out.println("Error" + string.substring(string.indexOf("message") + 8, ind));
    }
}


/*
    public static void getParser(String command) {
        Scanner sc = new Scanner(command);
        RestTemplate restTemplate = new RestTemplate();
        switch (sc.next()) {
            case ("operations"), ("operation") -> {
                ResponseEntity<String> responseEntity;
                if (sc.hasNextInt()) {
                    String url = "http://localhost:8080/operation/getById-";
                    responseEntity = restTemplate.getForEntity(url + sc.nextInt(), String.class);
                } else if (sc.hasNext()) {
                    String line = sc.nextLine();
                    String url = "http://localhost:8080/operation/getByName-";
                    responseEntity = restTemplate.getForEntity(url + sc.next(), String.class);
                } else {
                    String url = "http://localhost:8080/operation/get";
                    responseEntity = restTemplate.getForEntity(url, String.class);
                }
                stringParser(responseEntity.getBody());
            }*/
