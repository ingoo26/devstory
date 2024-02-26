package org.project.devstory.users;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.project.devstory.DataNotFoundException;
import org.project.devstory.follows.FollowRepository;
import org.project.devstory.follows.FollowService;
import org.project.devstory.follows.Follows;
import org.project.devstory.likes.LikeRepository;
import org.project.devstory.likes.Likes;
import org.project.devstory.posts.PostRepository;
import org.project.devstory.posts.Posts;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserCreateController {

    private final UserService userService;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final FollowService followService;


    @GetMapping("/signup")
    public String signup(@ModelAttribute UserCreateForm userCreateForm) {
        return "login";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute("userCreateForm") UserCreateForm userCreateForm, BindingResult bindingResult ,Users users, Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println(userCreateForm);
            System.out.println(bindingResult);
        }

        if (userCreateForm.getEmail().equals(userRepository.findByEmails(userCreateForm.getEmail()))) {
            bindingResult.rejectValue("email", "Email exist", "이미 존재하는 이메일입니다.");
            model.addAttribute("message", "이미 존재하는 이메일입니다.");
            model.addAttribute("searchUrl", "/user/signup");
            model.addAttribute("userCreateForm", userCreateForm);
            return "message";

        }
        try {
            userService.create(
                    userCreateForm.getEmail(),
                    userCreateForm.getPw1(),
                    userCreateForm.getName(),
                    userCreateForm.getBlogname(),
                    userCreateForm.getProfileimg()
            );

        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFaild", "이미 등록된 사용자입니다.");
            return "login";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFaild", e.getMessage());
            return "login";


        }


        return "redirect:/main";
    }

    @GetMapping(value = "/mypage/{id}")
    public String mypage(Model model, @PathVariable("id") Integer id, Principal principal) {
        Users user = this.userService.getUser(id);

        Users userId = userRepository.findByIds(id);

        Users loginUser = userRepository.findByNames(principal.getName());

        Follows follows = null;
        if (principal != null) {
            follows = this.followService.getFollowMy(id, principal.getName());
        }

        List<Posts> post = this.postRepository.findByUser(user);
        List<Likes> like = this.likeRepository.findByUser(user);
        Long follower = this.followRepository.countByFollower(userId);
        Long following = this.followRepository.countByFollowing(userId);

        boolean userAuth = false;

        model.addAttribute("postList", post);
        model.addAttribute("likeList", like);
        model.addAttribute("userAuth", userAuth);
        model.addAttribute("countFollower", follower);
        model.addAttribute("countFollowing", following);
        model.addAttribute("user", userId);
        model.addAttribute("loginUser", loginUser);
        model.addAttribute("follow", follows);
        return "userMyPage";
    }

    @GetMapping(value = "/mypage")
    public String myPage(Model model, Principal principal, @ModelAttribute UserEditForm userEditForm ) {
        String username = null;
        if (principal == null) {
            throw new NullPointerException("login user is not exist");
        } else {
            username = principal.getName();
        }

        Users userId = userRepository.findByNames(username);

        List<Posts> post = this.postRepository.findByUser(userId);
        List<Likes> like = this.likeRepository.findByUser(userId);
        Long follower = this.followRepository.countByFollower(userId);
        Long following = this.followRepository.countByFollowing(userId);
        boolean userAuth = true;


        model.addAttribute("postList", post);
        model.addAttribute("likeList", like);
        model.addAttribute("countFollower", follower);
        model.addAttribute("countFollowing", following);
        model.addAttribute("userAuth", userAuth);
        model.addAttribute("user", userId);
        return "mypage";
    }

    @PostMapping("/edit")
    public String userEdit(@Valid @ModelAttribute("userEditForm") UserEditForm userEditForm, Model model) {

        try {
            userService.edit(
                    userEditForm.getId(),
                    userEditForm.getProfileimg(),
                    userEditForm.getIntro()
            );
        } catch (Exception e) {
            return "redirect:/user/mypage";
        }
        model.addAttribute("message", "수정 완료");
        model.addAttribute("searchUrl", "/user/mypage");


        return "message";
    }
    @GetMapping(value="/userout/{id}")
    public String userout(@PathVariable("id") Integer id ){
        try{
            userService.changeState(id);

            return "redirect:/user/logout";
        }catch (DataNotFoundException e){
            return "redirect:/user/mypage";
        }

    }

}