package by.bsuir.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EncryptedLesson {
    private String encryptedLessonId;
    private String encryptedLessonIdSeed;
}
