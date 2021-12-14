package setung.delivery.utils.firebase;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import setung.delivery.controller.order.dto.OrderDto;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class FirestoreUtilTest {

    @Autowired
    FirestoreUtil firestoreUtil;

    @Test
    public void getBean() {
        assertThat(firestoreUtil).isNotNull();
    }

    @Test
    public void saveData() {
        String collectionName = "test";
        String documentName = "test2";
        OrderDto orderDto = OrderDto.builder()
                .id(2L)
                .address("test")
                .build();

        firestoreUtil.insertData(collectionName, documentName, orderDto);
        DocumentSnapshot data = firestoreUtil.findData(collectionName, documentName);

        OrderDto findOrderDto = data.toObject(OrderDto.class);

        assertThat(findOrderDto.getId()).isEqualTo(2L);
        assertThat(findOrderDto.getAddress()).isEqualTo("test");
    }
}