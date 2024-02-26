package org.project.devstory.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
//import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserCreateForm {

//    @Size(min = 2, max = 10)
    @NotEmpty(message = "사용자 이름을 입력해주세요")
    private String name;

    @Email
    @NotEmpty(message = "Email을 입력해주세요")
    private String email;

//    @Size(min = 4, max = 20)
    @NotEmpty(message = "비밀번호를 입력해주세요")
    private String pw1;

//    @Size(min = 4, max = 20)
    @NotEmpty(message = "비밀번호 확인을 입력해주세요")
    private String pw2;

//    @Size(min = 4, max = 20)
    @NotEmpty(message = "블로그 이름을 입력해주세요")
    private String blogname;

    private String profileimg;
}
