package ru.restuserclient.project.Model;

import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;

public record QuestionCreateRequestDto(
        @NotNull
        String title,
        @NotNull
        String description,
        @NotNull
        List<TagResponseDTO> tags) {

}