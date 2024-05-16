package com.study.jpa.chat05_practice.entity;


import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@ToString(exclude = {"post"})
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "tbl_hash_tag")
public class HashTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_no")
    private Long id;

    //N:1
    private String tagName; //해시태그 이름



    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)//같이 지워주세여(FK 있을때) : ON DELETE CASCADE 추가됨!!! : 삭제될 때에만
    @JoinColumn(name = "post_no") //FK 추가d : 연관관계의 주인
    private Post post;
}



