package org.project.devstory.likes;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.project.devstory.posts.Posts;
import org.project.devstory.users.Users;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Posts post;

    @Builder
    public Likes(Users user, Posts post) {
        this.user = user;
        this.post = post;
    }
}