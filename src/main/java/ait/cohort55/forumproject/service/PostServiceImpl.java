package ait.cohort55.forumproject.service;


import ait.cohort55.forumproject.repository.PostRepository;
import ait.cohort55.forumproject.dto.CommentAddDto;
import ait.cohort55.forumproject.dto.CommentDto;
import ait.cohort55.forumproject.dto.PostAddDto;
import ait.cohort55.forumproject.dto.PostDto;
import ait.cohort55.forumproject.dto.exception.NotFoundException;
import ait.cohort55.forumproject.model.Comment;
import ait.cohort55.forumproject.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    private PostDto mapToPostDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .createdDate(post.getCreatedDate())
                .tags(post.getTags())
                .likes(post.getLikes())
                .comments(post.getComments() != null ? post.getComments().stream()
                        .map(comment -> CommentDto.builder()
                                .user(comment.getUser())
                                .message(comment.getMessage())
                                .dateCreated(comment.getCreatedDate())
                                .likes(comment.getLikes())
                                .build())
                        .collect(Collectors.toList()) : List.of())
                .build();
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NotFoundException("Post with id " + id + " not found"));
        return mapToPostDto(post);
    }

    @Override
    public PostDto addLikeToPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NotFoundException("Post with id " + id + " not found"));
        post.setLikes(post.getLikes() + 1);
        postRepository.save(post);
        return mapToPostDto(post);
    }

    @Override
    public List<PostDto> getPostsByAuthor(String author) {
        List<Post> posts = postRepository.findByAuthorIgnoreCase(author);
        return posts.stream().map(this::mapToPostDto).collect(Collectors.toList());
    }

    @Override
    public PostDto addComment(Long id, String user, CommentAddDto commentAddDto) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NotFoundException("Post with id " + id + " not found"));

        Comment comment = Comment.builder()
                .user(user)
                .message(commentAddDto.getMessage())
                .createdDate(LocalDateTime.now())
                .likes(0)
                .build();

        if (post.getComments() == null) {
            post.setComments(new ArrayList<>());
        }
        post.getComments().add(comment);
        postRepository.save(post);
        return mapToPostDto(post);
    }

    @Override
    public void deletePost(Long id) {
        if (!postRepository.existsById(id)) {
            throw new NotFoundException("Post with id " + id + " not found");
        }
        postRepository.deleteById(id);
    }

    @Override
    public List<PostDto> getPostsByTags(List<String> tags) {
        List<Post> posts = postRepository.findByTagsIn(tags);
        return posts.stream()
                .map(this::mapToPostDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getPostsByPeriod(LocalDateTime from, LocalDateTime to) {
        List<Post> posts = postRepository.findByCreatedDateBetween(from, to);
        return posts.stream()
                .map(this::mapToPostDto)
                .collect(Collectors.toList());
    }

    @Override
    public PostDto updatePost(Long id, PostAddDto postAddDto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Post with id " + id + " not found"));
        post.setTitle(postAddDto.getTitle());
        post.setContent(postAddDto.getContent());
        post.setTags(postAddDto.getTags());

        postRepository.save(post);
        return mapToPostDto(post);

    }

    @Override
    public PostDto addPost(String user, PostAddDto postAddDto) {
        Post post = new Post();
        post.setAuthor(user);
        post.setTitle(postAddDto.getTitle());
        post.setContent(postAddDto.getContent());
        post.setTags(postAddDto.getTags());
        post.setCreatedDate(LocalDateTime.now());
        post.setLikes(0);
        post.setComments(List.of());
        Post savedPost = postRepository.save(post);
        return mapToPostDto(savedPost);
    }
}
