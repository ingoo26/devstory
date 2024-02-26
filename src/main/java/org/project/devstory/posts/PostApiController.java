package org.project.devstory.posts;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.project.devstory.DataNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.BindingResult;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;

    //새글 작성 하러 가기
    @GetMapping("/write")
    public ModelAndView writePost() throws IOException {
        // ~ 코드 생략
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("write");

        return modelAndView;
    }


    // 게시글 저장
    @PostMapping("/write")
    public Integer savePost(@RequestBody final PostRequest params, @AuthenticationPrincipal UserDetails userDetails) {
        String userEmail = userDetails.getUsername();

        return postService.savePost(params, userEmail);
    }

//    @GetMapping("/detail/{id}")
//    public ModelAndView detailPost(@PathVariable final Integer id) throws IOException {
//        // ~ 코드 생략
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("post");
//
//        return modelAndView;
//    }

//    // 게시글 상세정보 조회
//    @GetMapping("/{id}")
//    public PostResponse findPostById(@PathVariable final Integer id) {
//        return postService.findPostById(id);
//    }

    // 게시글 목록 조회
    @GetMapping
    public List<PostResponse> findAllPost() {
        return postService.findAllPost();
    }


}