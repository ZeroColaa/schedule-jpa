package io.github.zerocolaa.schedulejpa.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "author")
public class Author extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    public Author() {}

    public Author(String userName, String email, String password) {
        this.userName = userName;
        this.email      = email;
        this.password   = password;
    }

    public boolean isPasswordMatching(String password) {
        return this.password.equals(password);
    }
    public void updateAuthor(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }

}
