package setung.delivery.service.owner;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import setung.delivery.domain.owner.Owner;
import setung.delivery.exception.NotFoundException;
import setung.delivery.repository.OwnerRepository;
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
            throw new NotFoundException("email 혹은 password가 잘못되었습니다.");

        httpSession.setAttribute(OWNER_ID, owner.getId());
    }

    public void logout() {
        httpSession.removeAttribute(OWNER_ID);
    }

    public Owner getLoginOwner() {
        Long ownerId = (Long) httpSession.getAttribute(OWNER_ID);

        if(ownerId == null)
            throw new RuntimeException("로그인이 필요합니다.");

        Optional<Owner> owner = ownerRepository.findById(ownerId);

        return owner.get();
    }

}
