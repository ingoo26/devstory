package org.project.devstory.follows;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.project.devstory.users.UserRepository;
import org.project.devstory.users.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class FollowController {
    private final UserService userService;
    private final FollowService followService;
    private final UserRepository userRepository;

    @PutMapping("/follow")
    public void followUser(@RequestParam Map<String, String> params, Principal principal) {

        Integer follower = Integer.valueOf(params.get("userId"));
        Integer following = userRepository.findByNamess(principal.getName());
        if(following.equals(follower)) {
            throw new IllegalArgumentException("유저가 같음");
        }
        if (principal == null) {
            throw new NullPointerException("login user is not exist");
        } else {
            followService.createFollow(follower, principal.getName());
        }
    }
}
