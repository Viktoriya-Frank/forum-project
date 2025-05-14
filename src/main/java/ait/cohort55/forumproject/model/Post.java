package ait.cohort55.forumproject.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "posts")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {

    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createdDate;
    private List<String> tags;
    private int likes;
    private List<Comment> comments;



}
