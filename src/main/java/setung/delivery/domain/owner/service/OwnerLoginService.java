package setung.delivery.domain.owner.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import setung.delivery.domain.owner.model.Owner;
import setung.delivery.exception.CustomException;
import setung.delivery.exception.ErrorCode;
import setung.delivery.domain.owner.repository.OwnerRepository;
import setung.delivery.utils.SHA256;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OwnerLoginService {

    private final HttpSession httpSession;
    private final OwnerRepository ownerRepository;
    private final String OWNER_ID = "OWNERID";

    public void login(String email, String password) {
        password = SHA256.encBySha256(password);
        Owner owner = ownerRepository.findByEmailAndPassword(email, password);

        if (owner == null)
            throw new CustomException(ErrorCode.NOT_FOUND_OWNER);

        httpSession.setAttribute(OWNER_ID, owner.getId());
    }

    public void logout() {
        httpSession.removeAttribute(OWNER_ID);
    }

    public Owner getLoginOwner() {
        Long ownerId = (Long) httpSession.getAttribute(OWNER_ID);

        if(ownerId == null)
            throw new CustomException(ErrorCode.NEED_TO_LOGIN_OWNER);

        Optional<Owner> owner = ownerRepository.findById(ownerId);

        return owner.get();
    }

}
