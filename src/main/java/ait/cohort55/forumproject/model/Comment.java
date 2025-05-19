package ait.cohort55.forumproject.model;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class Comment {
    private String user;
    private String message;
    private LocalDateTime createdDate;
    private int likes;
}
