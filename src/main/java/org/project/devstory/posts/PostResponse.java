package org.project.devstory.posts;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

@Getter
public class
PostResponse {
    private Integer id;
    private String title;
    private LocalDateTime dates;
    private String content;
    private String postimg;
    private String user;

    public PostResponse(Posts post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.dates = post.getDates();
        this.content = post.getContent();
        this.postimg = post.getPostimg();
        this.user = post.getUser().getName();
    }

    public PostResponse(Page<Posts> posts) {
    }
}
