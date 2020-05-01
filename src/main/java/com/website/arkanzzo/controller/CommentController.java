package com.website.arkanzzo.controller;

import com.website.arkanzzo.domain.CommentDto;
import com.website.arkanzzo.mapper.CommentMapper;
import com.website.arkanzzo.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1")
public class CommentController {

    @Autowired
    CommentMapper mapper;

    @Autowired
    DbService service;

    @RequestMapping(method = RequestMethod.GET, value = "/comments")
    public List<CommentDto> getComments(){
        return mapper.mapToCommentDtoList(service.getAllComments());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/comments/{commentId}")
    public CommentDto getComment(@PathVariable Long commentId) throws CommentNotFoundException {
        return mapper.mapToCommentDto(service.getCommentById(commentId));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/comments/{commentId}")
    public void deleteComment(@PathVariable Long commentId) {
        service.deteleComment(commentId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/comments")
    public CommentDto updateComment(@RequestBody CommentDto commentDto) {
        return mapper.mapToCommentDto(service.saveComment(mapper.mapToComment(commentDto)));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/comments", consumes = APPLICATION_JSON_VALUE)
    public void createComment(@RequestBody CommentDto commentDto) {
        service.saveComment(mapper.mapToComment(commentDto));
    }

}
