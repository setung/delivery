package setung.delivery.service.rider;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import setung.delivery.domain.rider.Rider;
import setung.delivery.exception.CustomException;
import setung.delivery.exception.ErrorCode;
import setung.delivery.repository.RiderRepository;
import setung.delivery.utils.SHA256;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class RiderLoginService {

    private final HttpSession httpSession;
    private final RiderRepository riderRepository;
    private final String RIDER_ID = "RIDERID";

    public void login(String email, String password) {
        password = SHA256.encBySha256(password);
        Rider rider = riderRepository.findByEmailAndPassword(email, password);

        if (rider == null)
            throw new CustomException(ErrorCode.NOT_FOUND_RIDER);

        httpSession.setAttribute(RIDER_ID, rider.getId());
    }

    public void logout() {
        httpSession.removeAttribute(RIDER_ID);
    }

    public Rider getLoginRider() {
        Long riderId = (Long) httpSession.getAttribute(RIDER_ID);

        if(riderId == null)
            throw new CustomException(ErrorCode.NEED_TO_LOGIN_RIDER);

        return riderRepository.findById(riderId).get();
    }

}
