package org.project.devstory.follows;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.project.devstory.posts.PostRepository;
import org.project.devstory.users.UserRepository;
import org.project.devstory.users.Users;
import org.project.devstory.posts.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public void createFollow(Integer userId, String userName) {
        if (userName == null) {
            throw new IllegalArgumentException("유저 없음");
        }
        Users following = userRepository.findByName(userName)
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));

        Users follower = userRepository.findByIds(userId);
        boolean duplicateFollow = followRepository.existsByFollowingAndFollower(following, follower);
        if (duplicateFollow) {
            followRepository.deleteByFollowingAndFollower(following, follower);
        } else {
            Follows follows = Follows.builder()
                    .following(following)
                    .follower(follower).build();

            followRepository.save(follows);
        }
    }

    public Follows getFollow(Integer postId, String username) {

        if (username == null) {
            throw new IllegalArgumentException("유저 없음");
        }

        // postId를 기준으로 userId 찾기
        Users following = this.postRepository.findById(postId)
                .map(Posts::getUser)  // getUserId()는 실제 필드 및 메소드 이름에 따라 달라질 수 있습니다.
                .orElseThrow(() -> new IllegalArgumentException("포스트 없음"));
        Users follower = this.userRepository.findByName(username).orElseThrow(() -> new IllegalArgumentException("유저 없음"));
        Optional<Follows> follows = this.followRepository.findByFollowerAndFollowing(following, follower);
        System.out.println(follows);
        if (follows.isPresent()) {
            return follows.get();
        } else {
            return null;
        }
    }

    public Follows getFollowMy(Integer userId, String username) {

        if (username == null) {
            throw new IllegalArgumentException("유저 없음");
        }

        // postId를 기준으로 userId 찾기
        Users following = this.userRepository.findByIds(userId);
        Users follower = this.userRepository.findByName(username).orElseThrow(() -> new IllegalArgumentException("유저 없음"));
        Optional<Follows> follows = this.followRepository.findByFollowerAndFollowing(following, follower);
        System.out.println(follows);
        if (follows.isPresent()) {
            return follows.get();
        } else {
            return null;
        }
    }



}
