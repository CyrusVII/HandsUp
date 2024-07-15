package com.HandsUp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HandsUp.entities.Commento;
import com.HandsUp.entities.Post;

public interface CommentoRepository extends JpaRepository<Commento, Long> {

    List<Commento> findByPostId(Long postId);

	List<Commento> findByPost(Post post);
}
