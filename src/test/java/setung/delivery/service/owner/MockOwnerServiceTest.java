package setung.delivery.service.owner;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import setung.delivery.domain.owner.Owner;
import setung.delivery.repository.OwnerRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class MockOwnerServiceTest {

    @Mock
    OwnerRepository ownerRepository;

    @InjectMocks
    OwnerService ownerService;

    @Test
    @DisplayName("정상적인 Owner 계정 삭제")
    void deleteOwner() {
        Owner owner = Owner.builder().id(1L).build();

        ownerService.deleteOwner(owner.getId());

        verify(ownerRepository,times(1)).deleteById(owner.getId());
    }
}