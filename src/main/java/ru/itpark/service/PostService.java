package ru.itpark.service;


import ru.itpark.models.Post;
import java.util.List;

public interface PostService {
    List<Post> reverseList(List<Post> list);
}
