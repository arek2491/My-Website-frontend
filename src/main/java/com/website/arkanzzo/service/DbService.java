package com.website.arkanzzo.service;

import com.website.arkanzzo.controller.CommentNotFoundException;
import com.website.arkanzzo.domain.Comment;
import com.website.arkanzzo.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DbService {

    @Autowired
    private CommentRepository repository;

    public List<Comment> getAllComments() {
        return repository.findAll();
    }

    public Comment getCommentById(final Long commentId) throws CommentNotFoundException {
        return repository.findById(commentId).orElseThrow(CommentNotFoundException::new);
    }

    public Comment saveComment(final Comment comment) {
        return repository.save(comment);
    }

    public void deteleComment(final Long commentId) {
        repository.deleteById(commentId);
    }
}

