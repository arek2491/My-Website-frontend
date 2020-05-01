package com.website.arkanzzo.repository;

import com.website.arkanzzo.domain.Comment;

import org.springframework.data.repository.CrudRepository;


import java.util.List;
import java.util.Optional;


public interface CommentRepository extends CrudRepository<Comment, Long> {

    @Override
    List<Comment> findAll();

    @Override
    Optional<Comment> findById(Long commentId);

    @Override
    Comment save(Comment comment);

    @Override
    void deleteById(Long commentId);

    @Override
    long count();
}
