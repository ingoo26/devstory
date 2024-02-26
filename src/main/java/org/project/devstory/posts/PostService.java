package org.project.devstory.posts;

import lombok.RequiredArgsConstructor;
import org.project.devstory.DataNotFoundException;
import org.project.devstory.follows.FollowRepository;
import org.project.devstory.follows.FollowService;
import org.project.devstory.follows.Follows;
import org.project.devstory.likes.LikeRepository;
import org.project.devstory.likes.Likes;
import org.project.devstory.users.UserRepository;
import org.project.devstory.users.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;//주입
    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    // 게시글 저장
    @Transactional
    public Integer savePost(final PostRequest params, final String userEmail) {
        Users user = userRepository.findByName(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("user not found:" + userEmail));
        Posts posts = postRepository.save(params.toEntity(user));
        return posts.getId();
    }

    //게시글 수정
    @Transactional
    public Integer editPost(Posts post, final String userEmail) {
        Users user = userRepository.findByName(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("user not found:" + userEmail));

        Posts posts = postRepository.save(post.toEntity(user));
//  도전...       Posts posts = postRepository.save(post);는 업데이트 안되고 새로운 저장만 됨.
        return posts.getId();
    }

//    @Transactional
//    public Integer editPost(final Integer id, final String userEmail) {
//        Users user = userRepository.findByName(userEmail)
//                .orElseThrow(() -> new IllegalArgumentException("user not found:" + userEmail));
//        Posts posts = postRepository.save(post.toEntity(user));
////  도전...       Posts posts = postRepository.save(post);는 업데이트 안되고 새로운 저장만 됨.
//        return posts.getId();
//    }

    // 게시글 상세정보 조회
    @Transactional(readOnly = true)
    public PostResponse findPostById(final Integer id) {
        Posts posts = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("post not found : " + id));
        return new PostResponse(posts);
    }

    // 게시글 목록 조회
    @Transactional(readOnly = true)
    public List<PostResponse> findAllPost() {
        List<Posts> posts = postRepository.findAll();
        return posts.stream()
                .map(post -> new PostResponse(post))
                .collect(Collectors.toList());
    }

    @Transactional
    public Posts getpost(Integer id) {
        Optional<Posts> post = this.postRepository.findById(id);
        if (post.isPresent()) {
            return post.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }

    //게시글 목록 페이징 처리
    @Transactional(readOnly = true)
    public Page<Posts> postsList(Pageable pageable) {
        return postRepository.findAll(pageable);
    }
    //게시글 검색
    public Page<Posts> postSearchList(String searchKeyword, Pageable pageable) {
        return postRepository.findByTitleContaining(searchKeyword, pageable);
    }

    //인기순 정렬 (팔로우)
    @Transactional
    public Page<Posts>getFollowPostList(String username, Pageable pageable) {
        Users loggedInUser = userRepository.findByNames(username);

        if (loggedInUser != null) {
            List<Follows> followings = loggedInUser.getFollowings();
            List<Posts> postsOfFollowedUsers = new ArrayList<>();

            for (Follows follow : followings) {
                Users followedUser = follow.getFollower();
                List<Posts> postsOfFollowedUser = postRepository.findByUserOrderByDatesDesc(followedUser);
                postsOfFollowedUsers.addAll(postsOfFollowedUser);
            }

            int pageSize = pageable.getPageSize();
            int currentPage = pageable.getPageNumber();
            int startItem = currentPage * pageSize;

            List<Posts> paginatedList;

            if (postsOfFollowedUsers.size() < startItem) {
                paginatedList = Collections.emptyList();
            } else {
                int toIndex = Math.min(startItem + pageSize, postsOfFollowedUsers.size());
                paginatedList = postsOfFollowedUsers.subList(startItem, toIndex);
            }

            return new PageImpl<>(paginatedList, PageRequest.of(currentPage, pageSize), postsOfFollowedUsers.size());
        } else {
            // 사용자가 존재하지 않는 경우에 대한 처리
            return new PageImpl<>(Collections.emptyList());
        }
    }

    //게시글 삭제
    public void postDelete(Integer id) {
        postRepository.deleteById(id);
    }



}