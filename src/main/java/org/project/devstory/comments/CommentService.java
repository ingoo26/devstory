package org.project.devstory.comments;

import jakarta.transaction.Transactional;
import org.project.devstory.posts.Posts;
import lombok.RequiredArgsConstructor;
import org.project.devstory.users.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import org.project.devstory.users.Users;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional
    public void create(Posts post, String content, String username) {
        Comments comments = new Comments();
        comments.setContent(content);
        comments.setDates(LocalDateTime.now());
        comments.setPost(post);
        Users user = userRepository.findByName(username)
                .orElseThrow(() -> new IllegalArgumentException("user not found:" + username));
        comments.setUser(user);
        this.commentRepository.save(comments);

    }
    public void commentDelete(Integer id) {
        commentRepository.deleteById(id);
    }


}
