package ru.itpark.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.itpark.models.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByIdUserToOrderById(Long id);
    Optional<Post> findOneById(Long id);

}
