package ru.itpark.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itpark.models.Post;
import ru.itpark.repositories.PostRepository;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public List<Post> reverseList(List<Post> list) {
        List<Post> reverseList = new ArrayList<>();
        for (int i = list.size() - 1; i >= 0; i--) {
            reverseList.add(list.get(i));
        }
        return reverseList;
    }

    @Override
    public List<Post> getPostsUserTo(Long id) {
        return postRepository.findAllByIdUserToOrderById(id);
    }
}
