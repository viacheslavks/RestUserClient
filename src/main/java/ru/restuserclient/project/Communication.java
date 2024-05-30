package ru.restuserclient.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.restuserclient.project.Model.QuestionCreateRequestDto;
import ru.restuserclient.project.Model.TagResponseDTO;
import ru.restuserclient.project.Model.User;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;


import java.util.List;

import static java.util.Arrays.stream;

@Component
public class Communication {

    final RestTemplate restTemplate;
    Logger logger = LoggerFactory.getLogger(Communication.class);

    private final String URL = "http://94.198.50.185:7081/api/users";
    private final String DELETEURL = "http://94.198.50.185:7081/api/users/3";

    private final String SENDURL = "http://localhost:8181/api/user/question?accountId=3";
    private final String SENDURLTWO = "http://localhost:8181/api/user/question?accountId=2";

    private final String SEND3 = "http://localhost:8181/api/user/tag/top-3tags";

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
        List<TagResponseDTO> tags = new ArrayList<>();
        TagResponseDTO tag = new TagResponseDTO(null, "VeryNewTagWithoutIdAndDescription", null);
        tags.add(tag);
        QuestionCreateRequestDto questionCreateRequestDto = new QuestionCreateRequestDto("Test ТестЗаголовокПервый", "Test ТестОписание", tags);
        System.out.println(questionCreateRequestDto);
        HttpEntity<QuestionCreateRequestDto> sendQuestion = new HttpEntity<>(questionCreateRequestDto, headers);
        ResponseEntity<String> result = restTemplate.exchange(SENDURL, HttpMethod.POST, sendQuestion, String.class);
        String postQuestion = result.getBody();
        System.out.println(postQuestion);
        return  postQuestion;

    }
    public String sendQuestionDtos() {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        List<TagResponseDTO> tags = new ArrayList<>();
        TagResponseDTO tag = new TagResponseDTO(11L, "Testing11Tag", "This is the 11 description of the Tag");
        tags.add(tag);

        QuestionCreateRequestDto questionCreateRequestDto = new QuestionCreateRequestDto("UltraVeryNewTheTitleTest", "This is  ultra very new next description of test", tags);
        logger.info("Sending question: {}", questionCreateRequestDto);
        System.out.println(questionCreateRequestDto);

        HttpEntity<QuestionCreateRequestDto> sendQuestion = new HttpEntity<>(questionCreateRequestDto, headers);

        ResponseEntity<String> result = restTemplate.exchange(SENDURLTWO, HttpMethod.POST, sendQuestion, String.class);

        if(result.getStatusCode() == HttpStatus.OK) {
            String postQuestion = result.getBody();
            System.out.println(postQuestion);
            return postQuestion;
        } else {
            // Обработка ошибки, например, вывод сообщения об ошибке
            System.err.println("Failed to send question. Status code: " + result.getStatusCodeValue());
            return null;
        }
    }
    public List<TagResponseDTO> getTop3TagsByAccountId(Long accountId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<TagResponseDTO[]> response = restTemplate.exchange(
                SEND3 + "?accountId=" + accountId,
                HttpMethod.GET,
                entity,
                TagResponseDTO[].class
        );

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return List.of(response.getBody());
        } else {
            throw new RuntimeException("Failed to retrieve tags");
        }
    }
    public String getTop3TagsByAccountId1(Long accountId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(headers);
        logger.info("Sending request to get top 3 tags for accountId: {}", accountId);

        ResponseEntity<String> response = restTemplate.exchange(
                SEND3 + "?accountId=" + accountId,
                HttpMethod.GET,
                entity,
                String.class
        );

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            logger.info("Successfully retrieved tags for accountId: {}", accountId);
            logger.info("Response: {}", response.getBody());
            return response.getBody();

        } else {
            logger.error("Failed to retrieve tags for accountId: {}", accountId);
            throw new RuntimeException("Failed to retrieve tags");
        }
    }


}
