package org.project.devstory.likes;

import lombok.RequiredArgsConstructor;
import org.project.devstory.posts.Posts;
import org.project.devstory.users.Users;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;
    @PutMapping("/post/like")
    public void likePost(@RequestParam Map<String, String> params, Principal principal){

        Integer postId = Integer.valueOf(params.get("postNum"));
        if(principal == null) {
            throw new NullPointerException("login user is not exist");
        } else {
            likeService.createLike(postId, principal.getName());
        }

    }
}