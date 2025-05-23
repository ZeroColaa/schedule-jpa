package io.github.zerocolaa.schedulejpa.service;

import io.github.zerocolaa.schedulejpa.config.PasswordEncoder;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final PasswordEncoder passwordEncoder;

    //회원가입
    public SignUpResponseDto signUp(SignUpRequestDto requestDto) {


        if (authorRepository.existsByEmail(requestDto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다.");
        }

        Author author = new Author(
                requestDto.getUserName(),
                requestDto.getEmail(),
                passwordEncoder.encode(requestDto.getPassword())
        );

        Author saved = authorRepository.save(author);
        return SignUpResponseDto.from(saved);
    }

    //로그인
    public Author login(LoginRequestDto requestDto) {

        Author author = authorRepository.findByEmailOrElseThrow(requestDto.getEmail());

        if (!passwordEncoder.matches(requestDto.getPassword(), author.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "이메일/비밀번호 불일치");
        }

        return author;
    }

    //단건 조회
    @Transactional(readOnly = true)
    public AuthorResponseDto findAuthorById(Long authorId) {
        Author author = authorRepository.findByIdOrElseThrow(authorId);

        return AuthorResponseDto.from(author);
    }


    //작성자 이름 && 이메일 수정
    public AuthorResponseDto updateAuthor(Long authorId, UpdateAuthorRequestDto requestDto) {

        Author author = authorRepository.findByIdOrElseThrow(authorId);

        author.updateAuthor(requestDto.getUserName(), requestDto.getEmail());

        Author updated = authorRepository.save(author);

        return AuthorResponseDto.from(updated);
    }

    //작성자 비밀번호 수정
    public void updatePassword(Long authorId, UpdatePasswordRequestDto dto) {

        Author author = authorRepository.findByIdOrElseThrow(authorId);

        if (!passwordEncoder.matches(dto.getCurrentPassword(), author.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "현재 비밀번호가 일치하지 않습니다.");
        }

        author.changePassword(passwordEncoder.encode(dto.getNewPassword()));
    }

    //삭제
    public void deleteAuthor(Long authorId) {
        Author author = authorRepository.findByIdOrElseThrow(authorId);
        authorRepository.delete(author);
    }

    //로그아웃
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


