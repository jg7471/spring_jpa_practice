package com.study.jpa.chat05_practice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.study.jpa.chat05_practice.entity.HashTag;
import com.study.jpa.chat05_practice.entity.Post;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDetailResponseDTO {

    private String writer;
    private String title;
    private String content;
    private List<String> hashTags; //tagName만 가져오려고(다른 값 No 필요)

    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDateTime regDate;

    //엔터티를 DTO로 변환하는 생성자

    public PostDetailResponseDTO(Post post) {
        this.writer = post.getWriter();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.regDate = post.getCreateDate();
        this.hashTags = post.getHashTags()
                .stream() //stream list로 변환
                //.map(hash -> hash.getTagName()) //TagName 꺼내고
                .map(HashTag::getTagName) //메서드 참조 위와 同
                .collect(Collectors.toList()); //포장


    }
}
