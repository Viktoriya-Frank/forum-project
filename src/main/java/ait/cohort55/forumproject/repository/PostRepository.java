package ait.cohort55.forumproject.repository;


import ait.cohort55.forumproject.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends MongoRepository<Post, Long> {
    List<Post> findByAuthorIgnoreCase(String author);
    List<Post> findByTagsIn(List<String> tags);
    List<Post> findByCreatedDateBetween(LocalDateTime from, LocalDateTime to);
    Optional<Post> findById(Long id);
}
