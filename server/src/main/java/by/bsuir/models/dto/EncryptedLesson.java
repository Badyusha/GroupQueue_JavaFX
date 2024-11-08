package by.bsuir.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class EncryptedLesson implements Serializable {
    private String encryptedLessonId;
    private String encryptedLessonIdSeed;
}
