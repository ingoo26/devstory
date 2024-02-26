package org.project.devstory.posts;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.project.devstory.users.Users;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostRequest { //에디터 데이터를 저장 및 수정 을 처리할 요청 담당 클래스

    private String title;
    private String content;
    private Users userEmail;
    private String postimg;


    public Posts toEntity(Users user) {
        return Posts.builder()
                .title(title)
                .content(content)
                .postimg(postimg)
                .user(user)
                .build();
    }
}
