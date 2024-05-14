package com.study.jpa.chat05_practice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString(exclude = {"hashTags"})
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "tbl_post")
public class Post {

    @OneToMany(mappedBy = "post") //연관관계 주인x : mappedBy 조회만 @@
    List<HashTag> hashTags = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_no")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false) //notnull
    private String writer;

    private String content;
    @CreationTimestamp
    @Column(updatable = false) //수정不可

    private LocalDateTime createDate;

    @UpdateTimestamp //자동 업데이트
    private LocalDateTime updateDate;

    //private Post post; ???

}
