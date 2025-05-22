package io.github.zerocolaa.schedulejpa.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "schedule")
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "longtext")
    private String contents;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    public Schedule() {}

    public Schedule(String title, String contents) {
        this.title    = title;
        this.contents = contents;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void updateSchedule(String title, String contents) {
        this.title    = title;
        this.contents = contents;
    }
}
