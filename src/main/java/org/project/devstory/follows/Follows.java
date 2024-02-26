package org.project.devstory.follows;

import jakarta.persistence.*;
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
public class Follows {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "followings")
    private Users following;

    @ManyToOne
    @JoinColumn(name = "followers")
    private Users follower;

    @Builder
    public Follows(Users following, Users follower) {
        this.following = following;
        this.follower = follower;
    }
}
