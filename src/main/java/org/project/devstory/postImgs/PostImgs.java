package org.project.devstory.postImgs;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.project.devstory.posts.Posts;

@Getter
@Setter
@Entity
public class PostImgs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Integer id;

    @NotNull
    private String post_image_name;

    @ManyToOne
    @NotNull
    private Posts post;
}
