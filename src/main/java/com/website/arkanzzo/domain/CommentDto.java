package com.website.arkanzzo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class CommentDto {
    private Long id;
    private String nick;
    private String comment;
}
