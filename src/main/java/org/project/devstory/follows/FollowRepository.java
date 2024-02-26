package org.project.devstory.follows;

import org.project.devstory.posts.Posts;
import org.project.devstory.users.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follows,Integer> {
    void deleteByFollowingAndFollower(Users following, Users follower);

    boolean existsByFollowingAndFollower(Users following, Users follower);

    Optional<Follows> findByFollowerAndFollowing(Users follower, Users following);

    Long countByFollower(Users user);

    Long countByFollowing(Users user);


}
