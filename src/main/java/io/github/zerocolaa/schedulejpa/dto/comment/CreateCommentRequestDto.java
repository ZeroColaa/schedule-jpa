package io.github.zerocolaa.schedulejpa.dto.comment;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreateCommentRequestDto {

    @NotBlank
    private final String content;

    public CreateCommentRequestDto(String content) {
        this.content = content;
    }

}
