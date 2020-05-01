package com.website.arkanzzo.mapper;

import com.website.arkanzzo.domain.Comment;
import com.website.arkanzzo.domain.CommentDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentMapper {

    public Comment mapToComment(final CommentDto commentDto) {
        return new Comment(commentDto.getId(), commentDto.getNick(), commentDto.getComment());
    }

    public CommentDto mapToCommentDto(final Comment comment) {
        return new CommentDto(comment.getId(), comment.getNick(), comment.getComment());
    }

    public List<CommentDto> mapToCommentDtoList(final List<Comment> theList) {
        return theList.stream()
                .map(t -> new CommentDto(t.getId(), t.getNick(), t.getComment()))
                .collect(Collectors.toList());
    }
}

