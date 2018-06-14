package ru.itpark.service;


import org.springframework.stereotype.Service;
import ru.itpark.models.Post;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Override
    public List<Post> reverseList(List<Post> list) {
        List<Post> reverseList = new ArrayList<>();
        for (int i = list.size() - 1; i >= 0; i--) {
            reverseList.add(list.get(i));
        }
        return reverseList;
    }
}
