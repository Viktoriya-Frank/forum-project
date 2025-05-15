package ait.cohort55.forumproject.controller;


import ait.cohort55.forumproject.dto.CommentAddDto;
import ait.cohort55.forumproject.dto.PostAddDto;
import ait.cohort55.forumproject.dto.PostDto;
import ait.cohort55.forumproject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/forum/post")
public class PostController {


    private final PostService postService;


    @GetMapping("/{id}")
    public PostDto getPostById(@PathVariable String id) {
        return postService.getPostById(id);
    }

    @PostMapping("/{id}/like")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public PostDto addLikeToPost(@PathVariable String id) {
        return postService.addLikeToPost(id);
    }

    @GetMapping("/author/{author}")
    public List<PostDto> getPostsByAuthor(@PathVariable String author) {
        return postService.getPostsByAuthor(author);
    }

    @PatchMapping("/{postId}/comment/{id}")
    public PostDto addComment(@PathVariable String id, @PathVariable String postId, @PathVariable CommentAddDto commentAddDto) {
        return postService.addComment(id, postId, commentAddDto);
    }

    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable String postId) {
        postService.deletePost(postId);
    }

    @GetMapping("/tags")
    public List<PostDto> getPostsByTags(@PathVariable List<String> values) {
        return postService.getPostsByTags(values);
    }

    @GetMapping("/period")
    public List<PostDto> getPostsByPeriod(@PathVariable LocalDateTime from, LocalDateTime to) {
        return postService.getPostsByPeriod(from, to);
    }

    @PatchMapping("/{id}")
    public PostDto updatePost(@PathVariable String id, @RequestBody PostAddDto postAddDto) {
        return postService.updatePost(id, postAddDto);
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public PostDto addPost(@PathVariable String id, @RequestBody PostAddDto postAddDto) {
        return postService.addPost(id, postAddDto);
    }
}
