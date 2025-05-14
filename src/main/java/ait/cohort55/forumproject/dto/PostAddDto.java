package ait.cohort55.forumproject.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor

@Data
@Getter
public class PostAddDto {
    private String title;
    private String content;
    private List<String> tags;


}
