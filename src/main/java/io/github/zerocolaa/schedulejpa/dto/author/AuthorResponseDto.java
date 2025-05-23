package io.github.zerocolaa.schedulejpa.dto.author;


import io.github.zerocolaa.schedulejpa.entity.Author;
import lombok.Getter;

@Getter
public class AuthorResponseDto {

    private final Long  id;
    private final String userName;
    private final String email;


    private AuthorResponseDto(Long id, String userName, String email) {
        this.id       = id;
        this.userName = userName;
        this.email    = email;
    }

    public static AuthorResponseDto from(Author author) {
        return new AuthorResponseDto(author.getId(), author.getUserName(), author.getEmail());
    }
}
