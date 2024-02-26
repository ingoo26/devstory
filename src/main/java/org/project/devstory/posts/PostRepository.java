package org.project.devstory.posts;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.project.devstory.users.Users;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Posts,Integer> {

//    List<Posts> findAllBy(Users id);
    List<Posts> findByUser(Users user);

    // 최대 ID 조회
    @Query("select max(post.id) from Posts post")
    Integer findMaxPostId();

    Page<Posts> findByTitleContaining(String searchKeyword, Pageable pageable);

    List<Posts> findByUserIn(List<Users> users);

    List<Posts> findByUserOrderByDatesDesc(Users user);

    @Query("SELECT p FROM Posts p LEFT JOIN FETCH p.likes l GROUP BY p ORDER BY COUNT(l) DESC")
    Page<Posts> findAllOrderByLikeCountDesc(Pageable pageable);
}
