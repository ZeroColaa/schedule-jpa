package io.github.zerocolaa.schedulejpa.dto.author;

import io.github.zerocolaa.schedulejpa.entity.Author;
import lombok.Getter;

@Getter
public class SignUpRequestDto {

    private final String userName;
    private final String email;
    private final String password;

    public SignUpRequestDto(String userName, String email, String password) {
        this.userName = userName;
        this.email      = email;
        this.password   = password;
    }

    public Author toEntity() {
        return new Author(userName, email, password);
    }
}
