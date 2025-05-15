package ait.cohort55.forumproject.service;


import ait.cohort55.forumproject.dto.CommentAddDto;
import ait.cohort55.forumproject.dto.PostAddDto;
import ait.cohort55.forumproject.dto.PostDto;

import java.time.LocalDateTime;
import java.util.List;

public interface PostService {

    PostDto getPostById(String id);

    PostDto addLikeToPost(String id);

    List<PostDto> getPostsByAuthor(String author);

    PostDto addComment(String id, String user, CommentAddDto commentAddDto);

    void deletePost(String id);

    List<PostDto> getPostsByTags(List<String> tags);

    List<PostDto> getPostsByPeriod(LocalDateTime from, LocalDateTime to);

    PostDto updatePost(String id, PostAddDto postAddDto);

    PostDto addPost(String user, PostAddDto postAddDto);
}
