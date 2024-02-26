package org.project.devstory.admin;

import lombok.RequiredArgsConstructor;
import org.project.devstory.posts.PostRepository;
import org.project.devstory.posts.Posts;
import org.project.devstory.users.UserRepository;
import org.project.devstory.users.Users;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import org.project.devstory.DataNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AdminService {

    private final PostRepository postRepository;
    private final AdminRepository adminRepository;
    private final UserRepository userRepository;

    public List<Users> getUsers() {
        return this.adminRepository.findAll();
    }

    @Transactional
    public Users getUserId(Integer id) {
        Optional<Users> user = this.userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new DataNotFoundException("user not found");
        }
    }

    //회원관리 비활성화 State
    public void changeState(Integer id) {
        Users user = getUserId(id);
        if(user.getState() == 1) {
            user.setState(0);
        }else {
            user.setState(1);
        }
        userRepository.save(user);
    }
    //게시글 삭제
    public void postDelete(Integer id) {
        postRepository.deleteById(id);
    }

}



