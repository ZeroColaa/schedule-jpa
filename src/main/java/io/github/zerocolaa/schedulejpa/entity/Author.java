package io.github.zerocolaa.schedulejpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "author")
@Getter
@NoArgsConstructor
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



    public Author(String userName, String email, String password) {
        this.userName = userName;
        this.email      = email;
        this.password   = password;
    }


    public void updateAuthor(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }

    public void changePassword(String encodedNewPassword) {
        this.password = encodedNewPassword;
    }

}
