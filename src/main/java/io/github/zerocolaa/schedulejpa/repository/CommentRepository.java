package io.github.zerocolaa.schedulejpa.repository;

import io.github.zerocolaa.schedulejpa.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByScheduleId(Long scheduleId);

    default Comment findByIdOrElseThrow(Long commentId) {
        return findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."));
    }

}
