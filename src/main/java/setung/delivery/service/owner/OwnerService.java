package setung.delivery.service.owner;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import setung.delivery.domain.owner.Owner;
import setung.delivery.domain.owner.OwnerDto;
import setung.delivery.repository.OwnerRepository;
import setung.delivery.utils.SHA256;

@Service
@RequiredArgsConstructor
public class OwnerService {

    private final OwnerRepository ownerRepository;

    public Owner join(OwnerDto ownerDto) {
        ownerDto.setPassword(SHA256.encBySha256(ownerDto.getPassword()));
        Owner owner = ownerDto.toOwner();
        Owner savedOwner = ownerRepository.save(owner);
        return savedOwner;
    }


    public void deleteOwner(long ownerId) {
        ownerRepository.deleteById(ownerId);

    public Owner updateOwner(long ownerId, OwnerDto ownerDto) {
        ownerDto.setPassword(SHA256.encBySha256(ownerDto.getPassword()));

        Owner owner = ownerRepository.findById(ownerId).get();
        owner.updateOwner(ownerDto);
        return ownerRepository.save(owner);

    }
}
