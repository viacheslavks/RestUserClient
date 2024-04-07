package ru.restuserclient.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.restuserclient.project.Model.User;

import java.util.List;

@Component
public class Communication {

    @Autowired
    RestTemplate restTemplate;

    private final String URL = "http://94.198.50.185:7081/api/users";

    private List<User> getAllUsers() {
        return null;
    }

    private void saveUser(User user){

    }

    private void updateUser() {

    }

    private void deleteUser() {

    }



}
