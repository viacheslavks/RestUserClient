package ru.restuserclient.project;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.restuserclient.project.Model.TagResponseDTO;
import ru.restuserclient.project.configuration.MyConfig;

import java.util.List;

public class RestClient {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);

        Communication communication = context.getBean("communication", Communication.class);
//        String users = communication.getAllUsers();
//        communication.saveUser();
//        communication.updateUser();
//        communication.deleteUser();
//        communication.print();
//
        Long accountId = 2L;
        List<TagResponseDTO> tags =communication.getTop3TagsByAccountId(accountId);
        tags.forEach(tag -> System.out.println("Tag: " + tag));

        String Tag = communication.getTop3TagsByAccountId1(accountId);
        System.out.println(Tag);

//        context.close();
//        String question = communication.sendQuestionDto();
//        System.out.println(question);
//        String questionTwo = communication.sendQuestionDtos();
//        System.out.println(questionTwo);

    }
}
