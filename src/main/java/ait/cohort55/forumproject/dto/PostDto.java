package ait.cohort55.forumproject.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Getter
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createdDate;
    private List<String> tags;
    private int likes;
    private List<CommentDto> comments;


}
