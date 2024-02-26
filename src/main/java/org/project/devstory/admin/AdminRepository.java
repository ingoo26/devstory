package org.project.devstory.admin;

import org.project.devstory.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;



public interface AdminRepository extends JpaRepository<Users, Integer>{

}


