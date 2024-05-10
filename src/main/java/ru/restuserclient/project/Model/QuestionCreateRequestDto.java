package ru.restuserclient.project.Model;

import java.util.List;

public record QuestionCreateRequestDto(

        String title,
        String description,
        List<TagResponseDto> tags) {

}