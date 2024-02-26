package org.project.devstory.postImgs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.project.devstory.posts.Posts;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostImgDTO {
    private Integer id;
    private String post_image_name;
    private Posts post;

    public PostImgDTO(PostImgs entity) {
        this.id = entity.getId();
        this.post_image_name = entity.getPost_image_name();
        this.post = entity.getPost();
    }
}
