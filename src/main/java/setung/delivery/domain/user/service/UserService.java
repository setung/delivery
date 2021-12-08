package setung.delivery.domain.user.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import setung.delivery.domain.user.model.User;
import setung.delivery.controller.user.dto.UserDto;
import setung.delivery.exception.CustomException;
import setung.delivery.exception.ErrorCode;
import setung.delivery.domain.user.repository.UserRepository;
import setung.delivery.utils.SHA256;
import setung.delivery.utils.geo.GeocodingUtil;
import setung.delivery.utils.geo.LatLonData;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final GeocodingUtil geocodingUtil;

    public User join(UserDto userDto) {
        userDto.setPassword(SHA256.encBySha256(userDto.getPassword()));
        LatLonData latLon = geocodingUtil.getLatLon(userDto.getAddress());

        User user = new User(userDto);
        user.setLatLon(latLon);
        userRepository.save(user);
        return user;
    }

    public User findUserByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    public User updateUser(long userId, UserDto userDto) {
        userDto.setPassword(SHA256.encBySha256(userDto.getPassword()));
        LatLonData latLon = geocodingUtil.getLatLon(userDto.getAddress());

        User user = userRepository.findById(userId).get();
        user.updateUser(userDto);
        user.setLatLon(latLon);
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
