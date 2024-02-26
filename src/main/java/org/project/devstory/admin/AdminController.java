package org.project.devstory.admin;
import org.apache.catalina.User;
import org.project.devstory.DataNotFoundException;
import org.project.devstory.posts.PostResponse;
import org.project.devstory.posts.PostService;
import org.project.devstory.posts.Posts;
import org.project.devstory.users.UserRepository;
import org.project.devstory.users.UserService;
import org.project.devstory.users.Users;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserService userService;
    private final AdminService adminService;
    private final PostService postService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String admin(Model model) {
         List<Users> userList =this.adminService.getUsers();
         model.addAttribute("userList",userList);
         return "admin";

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/changeState/{id}")
    public String changeState(@PathVariable("id") Integer id) {
        try {
                adminService.changeState(id);

                return "redirect:/admin";

    }catch (DataNotFoundException e) {
            return "redirect:/main";
        }

    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/delete/{id}")
    public String deletePost(@PathVariable("id") Integer id) {
        postService.postDelete(id);
        return "redirect:/adminwrite";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/adminwrite")
    public String adminWrite(Model model) {
        List<PostResponse> postsList = this.postService.findAllPost();
        model.addAttribute("postsList", postsList);
        return "adminwrite";
    }


}


