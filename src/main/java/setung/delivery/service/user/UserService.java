package setung.delivery.service.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import setung.delivery.domain.user.User;
import setung.delivery.domain.user.UserDto;
import setung.delivery.exception.NotFoundException;
import setung.delivery.repository.user.UserRepository;
import setung.delivery.utils.SHA256;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User join(UserDto userDto) {
        userDto.setPassword(SHA256.encBySha256(userDto.getPassword()));
        User user = userRepository.save(userDto.toUser());
        return user;
    }

    public User findUserByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

}
