package ait.cohort55.forumproject.controller;


import ait.cohort55.forumproject.dto.CommentAddDto;
import ait.cohort55.forumproject.dto.PostAddDto;
import ait.cohort55.forumproject.dto.PostDto;
import ait.cohort55.forumproject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;


    @GetMapping("/forum/post/{postId}")
    public PostDto getPostById(@PathVariable String postId) {

        return postService.getPostById(postId);
    }

    @PostMapping("/forum/post/{postId}/like")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public PostDto addLikeToPost(@PathVariable String postId) {

        return postService.addLikeToPost(postId);
    }

    @GetMapping("/forum/posts/author/{user}")
    public List<PostDto> getPostsByAuthor(@PathVariable String user) {

        return postService.getPostsByAuthor(user);
    }

    @PatchMapping("/forum/post/{postId}/comment/{commenter}")
    public PostDto addComment(@PathVariable String postId,
                              @PathVariable String commenter,
                              @RequestBody CommentAddDto commentAddDto) {
        return postService.addComment(postId, commenter, commentAddDto);
    }

    @DeleteMapping("/forum/post/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable String postId) {

        postService.deletePost(postId);
    }

    @GetMapping("/forum/posts/tags")
    public List<PostDto> getPostsByTags(@RequestParam List<String> values) {
        return postService.getPostsByTags(values);
    }

    @GetMapping("/forum/posts/period")
    public List<PostDto> getPostsByPeriod(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return postService.getPostsByPeriod(from, to);
    }

    @PatchMapping("/forum/post/{postId}")
    public PostDto updatePost(@PathVariable String postId, @RequestBody PostAddDto postAddDto) {
        return postService.updatePost(postId, postAddDto);
    }

    @PostMapping("/forum/post/{user}")
    @ResponseStatus(HttpStatus.CREATED)
    public PostDto addPost(@PathVariable String user, @RequestBody PostAddDto postAddDto) {
        return postService.addPost(user, postAddDto);
    }
}
