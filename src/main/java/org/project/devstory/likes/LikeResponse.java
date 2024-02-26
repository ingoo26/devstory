package org.project.devstory.likes;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeResponse {

    private Integer userId;
    private Integer postId;

    public LikeResponse(Integer userId, Integer postId) {
        this.userId = userId;
        this.postId = postId;
    }
}