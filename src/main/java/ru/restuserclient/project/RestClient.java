package ru.restuserclient.project;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.restuserclient.project.configuration.MyConfig;

public class RestClient {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);

        Communication communication = context.getBean("communication", Communication.class);
        String users = communication.getAllUsers();
        communication.saveUser();
        communication.updateUser();
        communication.deleteUser();
        communication.print();


    }
}
