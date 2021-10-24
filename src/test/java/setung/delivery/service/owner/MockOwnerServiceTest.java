package setung.delivery.service.owner;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import setung.delivery.domain.owner.Owner;
import setung.delivery.domain.owner.OwnerDto;
import setung.delivery.repository.OwnerRepository;
import setung.delivery.utils.SHA256;

import java.util.Optional;

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
    void updateOwner() {
        OwnerDto ownerDto = OwnerDto.builder()
                .id(1L)
                .password("1234")
                .build();

        Owner owner = ownerDto.toOwner();

        when(ownerRepository.findById(1L)).thenReturn(Optional.ofNullable(owner));

        ownerService.updateOwner(ownerDto.getId(), ownerDto);

        verify(ownerRepository,times(1)).findById(ownerDto.getId());
        verify(ownerRepository, times(1)).save(owner);
    }
}