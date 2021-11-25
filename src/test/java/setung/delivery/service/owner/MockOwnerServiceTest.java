package setung.delivery.service.owner;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import setung.delivery.domain.owner.model.Owner;
import setung.delivery.domain.owner.repository.OwnerRepository;
import setung.delivery.controller.owner.dto.OwnerDto;
import setung.delivery.domain.owner.service.OwnerService;

import java.util.Optional;

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
  
    @Test
    @DisplayName("정상적인 Owner 회원 정보 ")
    void updateOwner() {
        OwnerDto ownerDto = OwnerDto.builder()
                .id(1L)
                .password("1234")
                .build();

        Owner owner = new Owner(ownerDto);

        when(ownerRepository.findById(1L)).thenReturn(Optional.ofNullable(owner));

        ownerService.updateOwner(ownerDto.getId(), ownerDto);

        verify(ownerRepository,times(1)).findById(ownerDto.getId());
        verify(ownerRepository, times(1)).save(owner);

    }
}