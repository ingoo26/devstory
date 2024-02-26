package org.project.devstory.tags;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.project.devstory.posts.Posts;

@Getter
@Setter
@Entity
public class Tags {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Integer id;

    @Column(length = 10)
    @NotNull
    private String tag_name;

    @ManyToOne
    @NotNull
    private Posts post;
}
