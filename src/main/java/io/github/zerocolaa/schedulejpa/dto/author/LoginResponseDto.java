package io.github.zerocolaa.schedulejpa.dto.author;

import io.github.zerocolaa.schedulejpa.entity.Author;
import lombok.Getter;

@Getter
public class LoginResponseDto {

    private final Long   id;
    private final String userName;
    private final String email;

    public LoginResponseDto(Author author) {
        this.id         = author.getId();
        this.userName = author.getUserName();
        this.email = author.getEmail();
    }
}
