package setung.delivery.utils.firebase;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import setung.delivery.exception.CustomException;
import setung.delivery.exception.ErrorCode;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FirebaseStorageUtil {

    private final Bucket bucket;

    public List<SavedFile> saveFile(List<MultipartFile> files) {
        List<SavedFile> savedFiles = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                String name = generateFileName(file.getOriginalFilename());
                bucket.create(name, file.getBytes(), file.getContentType());
                savedFiles.add(new SavedFile(name, file.getOriginalFilename()));
            } catch (Exception e) {
                throw new CustomException(ErrorCode.BAD_REQUEST_STORAGE);
            }
        }

        return savedFiles;
    }

    String generateFileName(String originalFileName) {
        if (getExtension(originalFileName) == null)
            throw new CustomException(ErrorCode.BAD_REQUEST_MENU);

        return UUID.randomUUID() + "." + getExtension(originalFileName);
    }

    String getExtension(String originalFileName) {
        return StringUtils.getFilenameExtension(originalFileName);
    }

    public void delete(String name) {
        if (name.isEmpty() || name == null) {
            throw new CustomException(ErrorCode.BAD_REQUEST_STORAGE, "name 값이 올바르지 않습니다.");
        }

        Blob blob = bucket.get(name);

        if (blob == null) {
            throw new CustomException(ErrorCode.BAD_REQUEST_STORAGE, "Storage에 존재하지 않는 파일입니다.");
        }

        blob.delete();
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SavedFile {
        private String id;
        private String fileName;
    }
}
