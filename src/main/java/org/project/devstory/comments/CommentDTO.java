package org.project.devstory.comments;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.project.devstory.posts.Posts;
import org.project.devstory.users.Users;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Integer id;
    private String comment_content;
    private LocalDateTime comment_date;
    private Posts comment_post;
    private Users comment_user;

    public CommentDTO(Comments entity) {
        this.id = entity.getId();
        this.comment_content = entity.getContent();
        this.comment_date = entity.getDates();
        this.comment_post = entity.getPost();
        this.comment_user = entity.getUser();
    }
}
