package org.project.devstory.users;

import lombok.*;
import org.project.devstory.users.Users;


import java.time.LocalDateTime;
@Data

@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Integer id;
    private String user_pw;
    private String user_email;
    private String user_name;
    private Integer user_role;
    private LocalDateTime user_created_at;
    private String user_profile_img;
    private String user_blog_name;
    private Integer user_state;

    public UserDTO(Users entity) {
        this.id = entity.getId();
        this.user_pw = entity.getPw();
        this.user_email = entity.getEmail();
        this.user_name = entity.getName();
        this.user_role = entity.getRole();
        this.user_created_at = entity.getCreatedat();
        this.user_profile_img = entity.getProfileimg();
        this.user_blog_name = entity.getBlogname();
        this.user_state = entity.getState();
    }
}