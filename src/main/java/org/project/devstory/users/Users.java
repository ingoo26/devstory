package org.project.devstory.users;

import jakarta.persistence.*;
import lombok.*;
import org.project.devstory.likes.Likes;
import org.project.devstory.posts.Posts;
import org.project.devstory.follows.Follows;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.project.devstory.posts.Posts;
import org.springframework.data.relational.core.sql.Like;

import java.util.ArrayList;
import java.util.List;

import java.time.LocalDateTime;

@DynamicInsert
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String pw;

    @Column (length = 30, unique = true)
    private String email;

    @Column (length = 10)
    private String name;

    @Column(columnDefinition = "Integer default 0")
    private Integer role;

    @CreationTimestamp
    private LocalDateTime createdat;

    private String profileimg = "logo.png";

    @Column (length = 20, unique = true)
    private String blogname;

    private String intro;

    @Column(columnDefinition = "Integer default 0")
    private Integer state;

    @OneToMany( mappedBy = "user")
    private List<Posts> postList;

    @OneToMany(mappedBy = "user")
    private List<Likes> likes = new ArrayList<>();

    @OneToMany(mappedBy = "following", fetch = FetchType.LAZY)
    private List<Follows> followings;

    @OneToMany(mappedBy = "follower", fetch = FetchType.LAZY)
    private List<Follows> followers;

    public Users toEntity() {
        return Users.builder()
                .state(state)
                .build();
    }
}
