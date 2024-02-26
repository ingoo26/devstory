package org.project.devstory.users;

import org.project.devstory.follows.Follows;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByEmail(String email);

    @Query(value = "select u.email from Users u where u.email = :email")
    String findByEmails(String email);

    Optional<Users> findById(Integer Id);

    @Query(value = "select u.id from Users u where u.name = :userid")
    Integer findByNamess(String userid);

    @Query(value = "select u from Users u where u.id = :id")
    Users findByIds(@Param("id") Integer id);

    Optional<Users> findByName(String name);

    @Query(value = "select u from Users u where u.name = :name")
    Users findByNames(String name);
}
