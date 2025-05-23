package io.github.zerocolaa.schedulejpa.dto.author;

import io.github.zerocolaa.schedulejpa.entity.Author;
import lombok.Getter;

@Getter
public class LoginResponseDto {

    private final Long   id;
    private final String userName;
    private final String email;

    private LoginResponseDto(Long id, String userName, String email) {
        this.id       = id;
        this.userName = userName;
        this.email    = email;
    }

    public static LoginResponseDto from(Author author) {
        return new LoginResponseDto(author.getId(), author.getUserName(), author.getEmail());
    }
}
