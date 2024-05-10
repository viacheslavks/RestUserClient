package ru.restuserclient.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.restuserclient.project.Model.QuestionCreateRequestDto;
import ru.restuserclient.project.Model.TagResponseDto;
import ru.restuserclient.project.Model.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Component
public class Communication {

    final RestTemplate restTemplate;
    Logger logger = LoggerFactory.getLogger(Communication.class);

    private final String URL = "http://94.198.50.185:7081/api/users";
    private final String DELETEURL = "http://94.198.50.185:7081/api/users/3";

    private final String SENDURL = "http://localhost:8181/api/user/question?accountId=1";
    public List<String> cookies;
    public StringBuilder stringBuilder = new StringBuilder();

    @Autowired
    public Communication(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getAllUsers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<String> result = restTemplate.exchange(URL, HttpMethod.GET, entity,
                String.class);

        cookies = result.getHeaders().get("Set-Cookie");
        String allUsers = result.getBody();
        System.out.println(allUsers);
        System.out.println(cookies);
        return allUsers;
    }

    public void saveUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", cookies.stream().collect(Collectors.joining(";")));
        User user = new User(3L, "James", "Brown", (byte) 33);
        HttpEntity<User> entity = new HttpEntity<User>(user, headers);
        ResponseEntity<String> result = restTemplate.exchange(URL, HttpMethod.POST, entity, String.class);
        String saveUser = result.getBody();
        System.out.println(saveUser);
        stringBuilder.append(saveUser);
    }

    public void updateUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", cookies.stream().collect(Collectors.joining(";")));
        User updateUser = new User(3L, "Thomas", "Shelby", (byte) 33);
        HttpEntity<User> update = new HttpEntity<User>(updateUser, headers);
        ResponseEntity<String> result = restTemplate.exchange(URL, HttpMethod.PUT, update, String.class);
        String upUser = result.getBody();
        System.out.println(upUser);
        stringBuilder.append(upUser);
    }


    public void deleteUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", cookies.stream().collect(Collectors.joining(";")));
        User updateUser = new User(3L, "Thomas", "Shelby", (byte) 33);
        HttpEntity<User> delete = new HttpEntity<User>(updateUser, headers);
        ResponseEntity<String> result = restTemplate.exchange(DELETEURL, HttpMethod.DELETE, delete, String.class);
        String delUser = result.getBody();
        System.out.println(delUser);
        stringBuilder.append(delUser);

    }

    public void print() {
        System.out.println(stringBuilder.toString());
    }

    public String sendQuestionDto() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        QuestionCreateRequestDto questionCreateRequestDto = new QuestionCreateRequestDto("ТестЗаголовок", "ТестОписание", new ArrayList<>());
        TagResponseDto tag = new TagResponseDto(1L, "Тэг", "Описание тэга");
        HttpEntity<QuestionCreateRequestDto> sendQuestion = new HttpEntity<>(questionCreateRequestDto, headers);
        ResponseEntity<String> result = restTemplate.exchange(SENDURL, HttpMethod.POST, sendQuestion, String.class);
        String postQuestion = result.getBody();
        System.out.println(postQuestion);
        return  postQuestion;

    }


}
