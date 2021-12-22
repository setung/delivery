package setung.delivery.utils.firebase;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import setung.delivery.exception.CustomException;
import setung.delivery.exception.ErrorCode;

@Component
@RequiredArgsConstructor
public class FirestoreUtil {

    private final Firestore firestore;

    public void insertData(String collectionName, String documentName, Object data) {
        firestore.collection(collectionName).document(documentName).set(data);
    }

    public DocumentSnapshot findData(String collectionName, String documentName) {
        DocumentReference document = firestore.collection(collectionName).document(documentName);
        ApiFuture<DocumentSnapshot> apiFuture = document.get();
        DocumentSnapshot documentSnapshot;

        try {
            documentSnapshot = apiFuture.get();
        } catch (Exception e) {
            throw new CustomException(ErrorCode.BAD_REQUEST_FIRESTORE);
        }

        return documentSnapshot;
    }
}
