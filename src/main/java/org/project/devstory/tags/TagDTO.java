package org.project.devstory.tags;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.project.devstory.posts.Posts;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TagDTO {
    private Integer id;
    private String tag_name;
    private Posts post;

    public TagDTO(Tags entity) {
        this.id = entity.getId();
        this.tag_name = entity.getTag_name();
        this.post = entity.getPost();
    }
}
