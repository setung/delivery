package setung.delivery.service.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import setung.delivery.domain.user.User;
import setung.delivery.exception.NotFoundException;
import setung.delivery.repository.user.UserRepository;
import setung.delivery.utils.SHA256;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserLoginService {

    private final HttpSession httpSession;
    private final UserRepository userRepository;
    private final String USER_ID = "USERID";

    public void login(String email, String password) {
        password = SHA256.encBySha256(password);
        User user = userRepository.findByEmailAndPassword(email, password);

        if (user == null)
            throw new NotFoundException("email 혹은 password가 잘못되었습니다.");

        httpSession.setAttribute(USER_ID, user.getId());
    }

    public void logout() {
        httpSession.removeAttribute(USER_ID);
    }

    public User getLoginUser() {
        Long userId = (Long) httpSession.getAttribute(USER_ID);

        if(userId == null)
            throw new RuntimeException("로그인이 필요합니다.");

        Optional<User> user = userRepository.findById(userId);

        return user.get();
    }

}
