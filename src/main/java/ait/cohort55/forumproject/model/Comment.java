package ait.cohort55.forumproject.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
@Document(collection = "comments")
public class Comment {
    private String user;
    private String message;
    private LocalDateTime createdDate;
    private int likes;
}
