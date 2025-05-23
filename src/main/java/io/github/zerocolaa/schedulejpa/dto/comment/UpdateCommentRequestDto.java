package io.github.zerocolaa.schedulejpa.dto.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateCommentRequestDto {
    @NotBlank
    private final String content;

    public UpdateCommentRequestDto(String content) {
        this.content = content;
    }
}