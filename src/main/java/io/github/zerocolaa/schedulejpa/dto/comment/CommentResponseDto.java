package io.github.zerocolaa.schedulejpa.dto.comment;


import io.github.zerocolaa.schedulejpa.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    private final Long id;
    private final String content;
    private final String userName;
    private final LocalDateTime createdDateTime;
    private final LocalDateTime modifiedDateTime;


    private CommentResponseDto(Long id, String content, String userName,
                               LocalDateTime created, LocalDateTime modified) {
        this.id               = id;
        this.content          = content;
        this.userName         = userName;
        this.createdDateTime  = created;
        this.modifiedDateTime = modified;
    }

    public static CommentResponseDto from(Comment comment) {
        return new CommentResponseDto(
                comment.getId(),
                comment.getContent(),
                comment.getAuthor().getUserName(),
                comment.getCreatedDateTime(),
                comment.getModifiedDateTime()
        );
    }
}
