package com.HandsUp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.HandsUp.entities.Post;
import com.HandsUp.repository.PostRepository;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post findById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    public void delete(Post post) {
        postRepository.delete(post);
    }
    
    public Post salvaPost(Post post) {
        return postRepository.save(post);
    }
}
