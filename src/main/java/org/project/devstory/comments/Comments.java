package org.project.devstory.comments;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.project.devstory.posts.Posts;
import org.project.devstory.users.Users;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column (length = 200)
    private String content;

    @CreationTimestamp
    private LocalDateTime dates;

    @ManyToOne
    private Posts post;

    @ManyToOne
    private Users user;
}
