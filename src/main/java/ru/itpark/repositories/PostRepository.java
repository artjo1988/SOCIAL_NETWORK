package ru.itpark.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.itpark.models.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
}
