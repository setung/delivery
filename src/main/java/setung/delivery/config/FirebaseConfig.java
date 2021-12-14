package setung.delivery.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.cloud.StorageClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Configuration
public class FirebaseConfig {

    @Value("${firebase.storage.bucket}")
    private String storageBucket;

    @PostConstruct
    public void initFirebase() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("firebase_key.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(classPathResource.getInputStream()))
                .setStorageBucket(storageBucket)
                .build();

        FirebaseApp.initializeApp(options);
    }

    @Bean
    public Storage getStorage() {
        return StorageOptions.getDefaultInstance().getService();
    }

    @Bean
    public Bucket getBucket() {
        return StorageClient.getInstance().bucket();
    }

    @Bean
    public Firestore firestore() {
        return FirestoreClient.getFirestore();
    }
}
