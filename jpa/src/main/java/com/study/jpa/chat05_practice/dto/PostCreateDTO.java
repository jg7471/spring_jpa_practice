package com.study.jpa.chat05_practice.dto;

import com.study.jpa.chat05_practice.entity.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Setter @Getter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCreateDTO {

    //검증하기
    //@NotNull -> null 허용x : "", "공백" 허용 **"" 빈문자열
    //@NotEmpty -> null, ""을 허용하지 않음, " "은 허용
    @NotBlank // null, "", "공백" 허용x
    @Size(min = 2, max = 5)
    private String writer;

    @NotBlank
    @Size(min = 1, max = 20)
    private String title;

    private String content;
    private List<String> hashTags;


    //dto를 entity로 변환하는 메서드
    public Post toEntity() {
        return Post.builder()
                .writer(writer)
                .title(title)
                .content(this.content) //this 생략 가능
                //.hashTags(hashTag) //entity에는 있으나 workbench에는 없음 : 조회용도임(OneToMany)
                .build();
    }

}
