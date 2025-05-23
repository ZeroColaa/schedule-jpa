package io.github.zerocolaa.schedulejpa.controller;

import io.github.zerocolaa.schedulejpa.dto.author.*;
import io.github.zerocolaa.schedulejpa.entity.Author;
import io.github.zerocolaa.schedulejpa.service.AuthorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody @Valid SignUpRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authorService.signUp(requestDto));
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto requestDto,
                                                  HttpServletRequest request) {
        Author loginUser = authorService.login(requestDto);
        request.getSession(true).setAttribute("LOGIN_USER_ID", loginUser.getId());

        return ResponseEntity.ok(LoginResponseDto.from(loginUser));
    }

    //로그아웃
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
       authorService.logout(request,response);
        return ResponseEntity.noContent().build();
    }


    //조회
    @GetMapping("/{authorId}")
    public ResponseEntity<AuthorResponseDto> findAuthor(@PathVariable Long authorId){
        return ResponseEntity.ok(authorService.findAuthorById(authorId));
    }

    //이름 && 이메일 수정
    @PutMapping("/{authorId}")
    public ResponseEntity<AuthorResponseDto> updateAuthor(@PathVariable Long authorId,
                                                    @RequestBody @Valid UpdateAuthorRequestDto requestDto) {
        return ResponseEntity.ok(authorService.updateAuthor(authorId, requestDto));
    }

    //비밀번호 수정
    @PutMapping("/{authorId}/password")
    public ResponseEntity<Void> updatePassword(
            @PathVariable Long authorId,
            @RequestBody @Valid UpdatePasswordRequestDto dto) {
        authorService.updatePassword(authorId, dto);
        return ResponseEntity.noContent().build();
    }

    //삭제
    @DeleteMapping("/{authorId}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long authorId){
        authorService.deleteAuthor(authorId);
        return ResponseEntity.noContent().build();
    }
}
