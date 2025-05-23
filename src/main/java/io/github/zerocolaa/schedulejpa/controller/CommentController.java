package io.github.zerocolaa.schedulejpa.controller;

import io.github.zerocolaa.schedulejpa.dto.comment.CommentResponseDto;
import io.github.zerocolaa.schedulejpa.dto.comment.CreateCommentRequestDto;
import io.github.zerocolaa.schedulejpa.dto.comment.UpdateCommentRequestDto;
import io.github.zerocolaa.schedulejpa.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/schedules/{scheduleId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(
            @PathVariable Long scheduleId,
            @RequestBody @Valid CreateCommentRequestDto requestDto,
            @SessionAttribute("LOGIN_USER_ID") Long userId
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentService.createComment(userId, scheduleId, requestDto));
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable Long scheduleId) {
        return ResponseEntity.ok(commentService.findCommentBySchedule(scheduleId));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @PathVariable Long commentId,
            @RequestBody @Valid UpdateCommentRequestDto requestDto,
            @SessionAttribute("LOGIN_USER_ID") Long userId
    ) {
        return ResponseEntity.ok(commentService.updateComment(commentId, requestDto.getContent(), userId));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long commentId,
            @SessionAttribute("LOGIN_USER_ID") Long userId
    ) {
        commentService.deleteComment(commentId, userId);
        return ResponseEntity.noContent().build();
    }
}

