package ait.cohort55.forumproject.service;


import ait.cohort55.forumproject.dto.CommentAddDto;
import ait.cohort55.forumproject.dto.PostAddDto;
import ait.cohort55.forumproject.dto.PostDto;

import java.time.LocalDateTime;
import java.util.List;

public interface PostService {

    PostDto getPostById(Long id);

    PostDto addLikeToPost(Long id);

    List<PostDto> getPostsByAuthor(String author);

    PostDto addComment(Long id, String user, CommentAddDto commentAddDto);


    void deletePost(Long id);

    List<PostDto> getPostsByTags(List<String> tags);

    List<PostDto> getPostsByPeriod(LocalDateTime from, LocalDateTime to);

    PostDto updatePost(Long id, PostAddDto postAddDto);

    PostDto addPost(String user, PostAddDto postAddDto);
}
