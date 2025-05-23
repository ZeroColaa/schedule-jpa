package io.github.zerocolaa.schedulejpa.service;


import io.github.zerocolaa.schedulejpa.dto.comment.CommentResponseDto;
import io.github.zerocolaa.schedulejpa.dto.comment.CreateCommentRequestDto;
import io.github.zerocolaa.schedulejpa.entity.Author;
import io.github.zerocolaa.schedulejpa.entity.Comment;
import io.github.zerocolaa.schedulejpa.entity.Schedule;
import io.github.zerocolaa.schedulejpa.repository.AuthorRepository;
import io.github.zerocolaa.schedulejpa.repository.CommentRepository;
import io.github.zerocolaa.schedulejpa.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final AuthorRepository authorRepository;
    private final ScheduleRepository scheduleRepository;

        //댓글 작성
    public CommentResponseDto createComment(Long authorId, Long scheduleId, CreateCommentRequestDto requestDto) {

        Author author = authorRepository.findByIdOrElseThrow(authorId);

        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId);

        Comment comment = new Comment(requestDto.getContent(), author, schedule);

        return CommentResponseDto.from(commentRepository.save(comment));
    }

    //일정 별로 모든 댓글 조회
    @Transactional(readOnly = true)
    public List<CommentResponseDto> findCommentBySchedule(Long scheduleId) {

        return commentRepository.findAllByScheduleId(scheduleId).stream()
                .map(CommentResponseDto::from).toList();
    }

    //댓글 수정
    public CommentResponseDto updateComment(Long commentId, String content, Long loginUserId) {
        Comment comment = findMyComment(commentId, loginUserId);
        comment.update(content);
        return CommentResponseDto.from(comment);
    }


    //댓글 삭제
    public void deleteComment(Long commentId, Long loginUserId) {
        Comment comment = findMyComment(commentId, loginUserId);
        commentRepository.delete(comment);
    }




    //댓글 조회 메서드
    private Comment findMyComment(Long commentId, Long loginUserId) {

        Comment comment = commentRepository.findByIdOrElseThrow(commentId);

        if (!comment.getAuthor().getId().equals(loginUserId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "댓글 수정/삭제 권한이 없습니다.");
        }
        return comment;
    }
}



