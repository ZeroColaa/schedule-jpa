package io.github.zerocolaa.schedulejpa.dto.author;

import io.github.zerocolaa.schedulejpa.entity.Author;
import lombok.Getter;

@Getter
public class SignUpResponseDto {

    private final Long   id;
    private final String userName;
    private final String email;

    public SignUpResponseDto(Author author) {
        this.id         = author.getId();
        this.userName = author.getUserName();
        this.email      = author.getEmail();
    }
}
