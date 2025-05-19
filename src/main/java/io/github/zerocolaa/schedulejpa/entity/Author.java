package io.github.zerocolaa.schedulejpa.entity;


import jakarta.persistence.*;


@Entity
public class Author extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String authorName;
    @Column(nullable = false)
    private String email;


    public Author(String authorName, String email) {
        this.authorName = authorName;
        this.email = email;
    }

    public Author() {
    }


}
