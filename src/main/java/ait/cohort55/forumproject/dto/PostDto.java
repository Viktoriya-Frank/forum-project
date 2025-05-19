package ait.cohort55.forumproject.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Getter
public class PostDto {
    private String id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createdDate;
    private List<String> tags;
    private Integer likes;
    private List<CommentDto> comments;


}
