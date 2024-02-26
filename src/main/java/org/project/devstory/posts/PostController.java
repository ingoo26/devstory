package org.project.devstory.posts;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.project.devstory.follows.FollowRepository;
import org.project.devstory.follows.FollowService;
import org.project.devstory.follows.Follows;
import org.project.devstory.likes.LikeRepository;
import org.project.devstory.likes.LikeService;
import org.project.devstory.likes.Likes;
import org.project.devstory.users.UserRepository;
import org.project.devstory.users.Users;
import org.springframework.boot.autoconfigure.cassandra.CassandraProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostRepository postRepository;

    private final PostService postService;
    private final LikeService likeService;
    private final FollowService followService;
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    @GetMapping("/main")
    public String main(Model model, @PageableDefault(page = 0, size = 6, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, String searchKeyword, HttpServletRequest request) {
        // 데이터를 담아 페이지로 보내기 위해 Model 자료형을 인자로 , 검색할 때 (searchKeyword가 있을 떄) 안할 때 구분해 if문 사용
        Page<Posts> list = null;
        if(searchKeyword != null) {  //검색할 때 (searchKeyword가 있을 때) 안할 때 구분
            list = postService.postSearchList(searchKeyword, pageable);
        } else {
            list = postService.postsList(pageable);
        }
        int nowPage = list.getPageable().getPageNumber() + 1; // 현재 페이지를 가져옴 , 0에서 시작하기에 처리를 위해 + 1
        int startPage = Math.max(nowPage - 4, 1); // Math.max(a, b) -- a 와 b 중 큰 값을 반환 --> 그냥 nowPAge - 4만 하면 nowpage가 1인 경우 -3도 가능하기에 이를 방지하기 위함
        int endPage = Math.min(nowPage + 5, list.getTotalPages()); // totalPage보다 크면 안되기에 두개 중 최소값 반환하는 Math.min을 사용

        model.addAttribute("postsList", list);
        // boardService에서 생성한 boardlist메소드를 통해 list가 반환되는데 해당 list를 "list"라는 이름으로 넘겨주겠다는 것(html에 나올 수 있게)
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("url", request.getRequestURI());

        return "layout";
    }

    /*메인페이지 정렬 _ 팔로우순 _인기 */
    @GetMapping(value="/main/follower")
    public String mainfollower(Model model, Principal principal, @PageableDefault(page = 0, size = 6, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, HttpServletRequest request){
        Page<Posts> postsList = postService.getFollowPostList(principal.getName(), pageable);

//        int nowPage = postsList.getPageable().getPageNumber() + 1; // 현재 페이지를 가져옴 , 0에서 시작하기에 처리를 위해 + 1
//        int startPage = Math.max(nowPage - 4, 1); // Math.max(a, b) -- a 와 b 중 큰 값을 반환 --> 그냥 nowPAge - 4만 하면 nowpage가 1인 경우 -3도 가능하기에 이를 방지하기 위함
//        int endPage = Math.min(nowPage + 5, list.getTotalPages()); // totalPage보다 크면 안되기에 두개 중 최소값 반환하는 Math.min을 사용

        int nowPage = postsList.getNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, postsList.getTotalPages());


        model.addAttribute("postsList", postsList);
        // boardService에서 생성한 boardlist메소드를 통해 list가 반환되는데 해당 list를 "list"라는 이름으로 넘겨주겠다는 것(html에 나올 수 있게)
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("url", request.getRequestURI());

        return "layout";

    }
    /*메인페이지 정렬 _ 좋아요순 _트렌드 */
    @GetMapping(value="main/trending")
    public String mainTrending(Model model, @PageableDefault(page = 0, size = 6, direction = Sort.Direction.DESC) Pageable pageable, HttpServletRequest request) {
        Page<Posts> postsList = postRepository.findAllOrderByLikeCountDesc(pageable);

        int nowPage = postsList.getPageable().getPageNumber() + 1; // 현재 페이지를 가져옴 , 0에서 시작하기에 처리를 위해 + 1
        int startPage = Math.max(nowPage - 4, 1); // Math.max(a, b) -- a 와 b 중 큰 값을 반환 --> 그냥 nowPAge - 4만 하면 nowpage가 1인 경우 -3도 가능하기에 이를 방지하기 위함
        int endPage = Math.min(nowPage + 5, postsList.getTotalPages()); // totalPage보다 크면 안되기에 두개 중 최소값 반환하는 Math.min을 사용

        model.addAttribute("postsList", postsList);
        // boardService에서 생성한 boardlist메소드를 통해 list가 반환되는데 해당 list를 "list"라는 이름으로 넘겨주겠다는 것(html에 나올 수 있게)
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("url", request.getRequestURI());

        return "layout";
    }



    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, Principal principal) {
        Posts post = this.postService.getpost(id);
        Long countLike = likeRepository.countByPost(post);

        Follows follows = null;
        Likes like = null;
        if(principal != null) {
            like = this.likeService.getlike(id, principal.getName());
            follows = this.followService.getFollow(id, principal.getName());
        }

        model.addAttribute("like", like);
        model.addAttribute("post", post);
        model.addAttribute("follow", follows);
        model.addAttribute("countLike", countLike);
        return "post";
    }



    //게시글 수정 버튼
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/post/edit/{id}")
    public String postEditForm(@PathVariable("id") Integer id, Model model) {
        Posts post = this.postService.getpost(id);
        model.addAttribute("post", post);
        return "postedit";
    }

    // 게시글 수정 저장
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/post/edit/{id}")
    public String update( @PathVariable("id") Integer id, @RequestBody final PostRequest params, @AuthenticationPrincipal UserDetails userDetails) {

        String userEmail = userDetails.getUsername();

        Posts boardTemp = postService.getpost(id);
        boardTemp.setContent(params.getContent());
        boardTemp.setTitle(params.getTitle());
        postService.editPost(boardTemp, userEmail);
        postService.postDelete(postRepository.findMaxPostId());


        return "redirect:/main";
    }


    //게시글 삭제
    @GetMapping("/post/delete/{id}")
    public String deletePost(@PathVariable("id") Integer id, Model model) {
        postService.postDelete(id);

        model.addAttribute("message", "게시글이 삭제 됐습니다.");
        model.addAttribute("searchUrl", "/main");
        return "message";
    }

}
