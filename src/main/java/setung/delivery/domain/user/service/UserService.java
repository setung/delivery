package setung.delivery.domain.user.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import setung.delivery.domain.user.model.User;
import setung.delivery.controller.user.dto.UserDto;
import setung.delivery.exception.CustomException;
import setung.delivery.exception.ErrorCode;
import setung.delivery.domain.user.repository.UserRepository;
import setung.delivery.utils.SHA256;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User join(UserDto userDto) {
        userDto.setPassword(SHA256.encBySha256(userDto.getPassword()));
        User user = userRepository.save(new User(userDto));
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

    public User findUserById(long userId) {
        Optional<User> user = userRepository.findById(userId);

        if (!user.isPresent())
            throw new CustomException(ErrorCode.NOT_FOUND_USER);

        return user.get();
    }

    public void deleteUser(long userId) {
        userRepository.deleteById(userId);
    }
}
