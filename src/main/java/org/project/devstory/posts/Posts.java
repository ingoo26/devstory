package org.project.devstory.posts;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.project.devstory.likes.Likes;
import org.project.devstory.users.Users;
import org.project.devstory.comments.Comments;
import org.springframework.data.relational.core.sql.Like;

import java.util.ArrayList;
import java.util.List;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "posts")
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 3000)
    private String content;

    private String postimg;

    @CreationTimestamp
    private LocalDateTime dates;

    @UpdateTimestamp
    private LocalDateTime updates;

    @ManyToOne
    private Users user;

    @OneToMany( mappedBy = "post")
    private List<Comments> commentsList;

    @OneToMany(mappedBy = "post")
    private Set<Likes> likes;


    @Builder
    public Posts(String title, String content, String postimg, Users user) {
        this.title = title;
        this.content = content;
        this.postimg = postimg;
        this.user = user;
    }


    public Posts toEntity(Users user) {
        return Posts.builder()
                .title(title)
                .content(content)
                .user(user)
                .build();
    }
}
