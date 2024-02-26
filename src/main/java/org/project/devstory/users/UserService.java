package org.project.devstory.users;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.project.devstory.DataNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Users create(String email, String pw, String name, String blogname, String profileimg) {
        Users user = new Users();

        user.setEmail(email);
        user.setPw(passwordEncoder.encode(pw));
        user.setName(name);
        user.setBlogname(blogname);
        user.setProfileimg(profileimg);
        this.userRepository.save(user);
        return user;
    }

    public Users edit(Integer id, String profileimg, String intro) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userid = authentication.getName();


        id = userRepository.findByNamess(userid);

        Optional<Users> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();

            user.setId(id);

            if(profileimg == "" && intro == "") {
                throw new NullPointerException("not exists profileimg and intro");
            } else if(profileimg == "" && intro != "") {
                user.setIntro(intro);
            } else if(profileimg != "" && intro == "") {
                user.setProfileimg(profileimg);
            } else {
                user.setProfileimg(profileimg);
                user.setIntro(intro);
            }

            user = userRepository.save(user);
            return user;
        } else {
            throw new RuntimeException("사용자를 찾을 수 없습니다.");
        }
    }

    public Users getUser(Integer id) {
        Optional<Users> user = this.userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }
    public void changeState(Integer id) {
        Users user = getUser(id);
        if(user.getState() == 0) {
            user.setState(1);
        }
        userRepository.save(user);
    }
}
