package org.project.devstory.comments;

import org.aspectj.bridge.Message;
import org.project.devstory.posts.Posts;
import lombok.RequiredArgsConstructor;
import org.project.devstory.posts.PostService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/comments", method = RequestMethod.GET)
@RequiredArgsConstructor
@Controller
public class CommentController {

    private final PostService postService;
    private final CommentService commentService;

    @PostMapping("/create/{id}")
    public String createComment(Model model, @PathVariable("id") Integer id, @RequestParam(value="content") String content, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        Posts posts = this.postService.getpost(id);
        this.commentService.create(posts, content, username);
        return String.format("redirect:/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String commentDelete(@PathVariable("id") Integer id, Model model) {
        commentService.commentDelete(id);
        model.addAttribute("message", "댓글이 삭제되었습니다.");
        model.addAttribute("searchUrl", "/main");
        return "message";
    }

}
