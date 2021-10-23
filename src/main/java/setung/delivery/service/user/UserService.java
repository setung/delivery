package setung.delivery.service.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import setung.delivery.domain.user.User;
import setung.delivery.domain.user.UserDto;
import setung.delivery.repository.UserRepository;
import setung.delivery.utils.SHA256;

import java.util.Optional;

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

    public User updateUser(long userId, UserDto userDto) {
        userDto.setPassword(SHA256.encBySha256(userDto.getPassword()));

        User user = userRepository.findById(userId).get();
        user.updateUser(userDto);
        userRepository.save(user);
        return user;
    }
}
