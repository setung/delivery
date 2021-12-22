package setung.delivery.utils.firebase;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import setung.delivery.controller.order.dto.OrderDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class FirestoreUtilTest {

    @Autowired
    FirestoreUtil firestoreUtil;

    @Autowired
    Firestore fireStore;

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

    @Test
    public void deleteAll() throws InterruptedException {
        Iterable<CollectionReference> collectionReferences = fireStore.listCollections();

        for (CollectionReference collectionReference : collectionReferences) {
            deleteCollection(collectionReference, 0);
        }
    }

    void deleteCollection(CollectionReference collection, int batchSize) {
        try {
            // retrieve a small batch of documents to avoid out-of-memory errors
            ApiFuture<QuerySnapshot> future = collection.limit(batchSize).get();
            int deleted = 0;
            // future.get() blocks on document retrieval
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                document.getReference().delete();
                ++deleted;
            }
            if (deleted >= batchSize) {
                // retrieve and delete another batch
                deleteCollection(collection, batchSize);
            }
        } catch (Exception e) {
            System.err.println("Error deleting collection : " + e.getMessage());
        }
    }
}