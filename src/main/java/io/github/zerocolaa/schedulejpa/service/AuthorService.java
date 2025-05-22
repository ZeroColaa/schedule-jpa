package io.github.zerocolaa.schedulejpa.service;

import io.github.zerocolaa.schedulejpa.dto.author.*;
import io.github.zerocolaa.schedulejpa.entity.Author;
import io.github.zerocolaa.schedulejpa.repository.AuthorRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    //회원가입
    public SignUpResponseDto signUp(SignUpRequestDto requestDto) {
        Author author = requestDto.toEntity();
        Author saved = authorRepository.save(author);
        return new SignUpResponseDto(saved);
    }

    //로그인
    public Author login(LoginRequestDto requestDto) {
        Author author = authorRepository.findByEmailOrElseThrow(requestDto.getEmail());
        if (!author. isPasswordMatching(requestDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "이메일/비밀번호 불일치");
        }
        return author;
    }

    //단건 조회
    public AuthorResponseDto findAuthorById(Long authorId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return new AuthorResponseDto(author);
    }


    //수정
    public AuthorResponseDto updateAuthor(Long authorId, UpdateAuthorRequestDto requestDto) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // 이름과 이메일만 수정
        author.updateAuthor(requestDto.getUserName(), requestDto.getEmail());
        Author updated = authorRepository.save(author);

        return new AuthorResponseDto(updated);
    }

    //삭제
    public void deleteAuthor(Long authorId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        authorRepository.delete(author);
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }
        Cookie cookie = new Cookie("JSESSIONID",null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}


