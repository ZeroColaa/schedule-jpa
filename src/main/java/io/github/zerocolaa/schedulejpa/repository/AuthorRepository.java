package io.github.zerocolaa.schedulejpa.repository;

import io.github.zerocolaa.schedulejpa.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findByEmail(String email);

    default Author findByIdOrElseThrow(Long authorId) {
        return findById(authorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "작성자를 찾을 수 없습니다."));
    }

    default Author findByEmailOrElseThrow(String email) {
        return findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"작성자를 찾을 수 없습니다."));
    }


    boolean existsByEmail(String email);
}
