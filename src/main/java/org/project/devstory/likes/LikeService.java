package org.project.devstory.likes;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.project.devstory.DataNotFoundException;
import org.project.devstory.follows.Follows;
import org.project.devstory.posts.PostRepository;
import org.project.devstory.posts.PostRequest;
import org.project.devstory.posts.PostService;
import org.project.devstory.posts.Posts;
import org.project.devstory.users.UserRepository;
import org.project.devstory.users.UserService;
import org.project.devstory.users.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostService postService;

    @Transactional
    public void createLike(Integer postId, String userName) {
        if (userName == null) {
            throw new IllegalArgumentException("유저 없음");
        }
        Users user = userRepository.findByName(userName)
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));

        Posts post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("게시글 없음"));
        boolean duplicateLike = likeRepository.existsByUserAndPost(user, post);
        if (duplicateLike) {
            likeRepository.deleteByUserAndPost(user, post);
        } else {
            Likes likes = Likes.builder()
                    .user(user)
                    .post(post).build();

            likeRepository.save(likes);
        }
    }

    public Likes getlike(Integer postId, String username) {
        if (username == null) {
            throw new IllegalArgumentException("유저 없음");
        }
        Posts post = this.postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("게시글 없음"));
        Users user = this.userRepository.findByName(username).orElseThrow(() -> new IllegalArgumentException("유저 없음"));
        Optional<Likes> like = this.likeRepository.findByUserAndPost(user, post);
        if (like.isPresent()) {
            return like.get();
        } else {
            return null;
        }
    }

    @Transactional
    public Page<Likes> likesList(Pageable pageable) {
        return likeRepository.findAll(pageable);
    }
}