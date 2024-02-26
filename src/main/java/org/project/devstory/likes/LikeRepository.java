package org.project.devstory.likes;

import org.project.devstory.posts.Posts;
import org.project.devstory.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Likes,Integer> {
    boolean existsByUserAndPost(Users user, Posts post);

    void deleteByUserAndPost(Users user, Posts post);

    Optional<Likes> findByUserAndPost(Users user, Posts post);

    List<Likes> findByUser(Users user);

    Long countByPost(Posts post);
}