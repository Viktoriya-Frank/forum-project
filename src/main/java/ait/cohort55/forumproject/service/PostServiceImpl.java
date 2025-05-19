package ait.cohort55.forumproject.service;

import ait.cohort55.forumproject.dto.exception.EmptyArgumentException;
import ait.cohort55.forumproject.dto.exception.NotFoundException;
import ait.cohort55.forumproject.repository.PostRepository;
import ait.cohort55.forumproject.dto.CommentAddDto;
import ait.cohort55.forumproject.dto.CommentDto;
import ait.cohort55.forumproject.dto.PostAddDto;
import ait.cohort55.forumproject.dto.PostDto;
import ait.cohort55.forumproject.model.Comment;
import ait.cohort55.forumproject.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public PostDto getPostById(String id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Post with id " + id + " not found"));
        return mapToPostDto(post);
    }

    @Override
    public PostDto addLikeToPost(String id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Post with id " + id + " not found"));
        post.setLikes(post.getLikes() + 1);
        postRepository.save(post);
        return mapToPostDto(post);
    }

    @Override
    public List<PostDto> getPostsByAuthor(String author) {
        return postRepository.findByAuthorIgnoreCase(author).stream()
                .map(this::mapToPostDto)
                .collect(Collectors.toList());
    }

    @Override
    public PostDto addComment(String id, String user, CommentAddDto commentAddDto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Post with id " + id + " not found"));

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
    public void deletePost(String id) {
        if (!postRepository.existsById(id)) {
            throw new NotFoundException("Post with id " + id + " not found");
        }
        postRepository.deleteById(id);
    }

    @Override
    public List<PostDto> getPostsByTags(List<String> tags) {
        return postRepository.findByTagsIn(tags).stream()
                .map(this::mapToPostDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getPostsByPeriod(LocalDateTime from, LocalDateTime to) {
        return postRepository.findByCreatedDateBetween(from, to).stream()
                .map(this::mapToPostDto)
                .collect(Collectors.toList());
    }

    @Override
    public PostDto updatePost(String id, PostAddDto postAddDto) {
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
        if (postAddDto == null) {
            throw new EmptyArgumentException("Data cannot be null");
        }

        String title = postAddDto.getTitle();
        if (title == null || title.isEmpty()) {
            throw new EmptyArgumentException("Title cannot be empty");
        }

        String content = postAddDto.getContent();
        if (content == null || content.isEmpty()) {
            throw new EmptyArgumentException("Content cannot be empty");
        }

        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setAuthor(user);
        post.setTags(postAddDto.getTags());
        post.setCreatedDate(LocalDateTime.now());
        post.setLikes(0);

        postRepository.save(post);
        return mapToPostDto(post);
    }

    private PostDto mapToPostDto(Post post) {
        List<CommentDto> comments = null;
        if (post.getComments() != null) {
            comments = post.getComments().stream()
                    .map(comment -> new CommentDto(
                            comment.getUser(),
                            comment.getMessage(),
                            comment.getCreatedDate(),
                            comment.getLikes()))
                    .collect(Collectors.toList());
        }

        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .createdDate(post.getCreatedDate())
                .likes(post.getLikes())
                .tags(post.getTags())
                .comments(comments)
                .build();
    }
}
