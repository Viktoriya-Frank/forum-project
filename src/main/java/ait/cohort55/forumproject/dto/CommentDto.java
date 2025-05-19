package ait.cohort55.forumproject.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Getter
public class CommentDto {

    private String user;
    private String message;
    private LocalDateTime dateCreated;
    private int likes;

}
