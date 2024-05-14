package com.study.jpa.chat05_practice.service;

import com.study.jpa.chat05_practice.dto.PageDTO;
import com.study.jpa.chat05_practice.dto.PageResponseDTO;
import com.study.jpa.chat05_practice.dto.PostDetailResponseDTO;
import com.study.jpa.chat05_practice.dto.PostListResponseDTO;
import com.study.jpa.chat05_practice.entity.Post;
import com.study.jpa.chat05_practice.repository.HashTagRepository;
import com.study.jpa.chat05_practice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor //@autoweitred X : 해당 필드들을 초기화하는 생성자가 자동으로 생성
@Transactional //스프링꺼 : JPA repository는 트랜잭션 단위로 동작하기 때문에 작성必
public class PostService {

    private final PostRepository postRepository; //생성자를 통해 초기화@@
    private final HashTagRepository hashTagRepository;

    public PostListResponseDTO getPosts(PageDTO dto){
        //Pageable 객체 생성

        //Pageable 객체 생성
        //PageRequest.of()는 Spring Data JPA에서 페이지네이션을 위한 PageRequest 객체를 생성하는 정적 팩토리 메서드
        Pageable pageable = PageRequest.of(
                dto.getPage() -1,
                dto.getSize(),
                Sort.by("createDate").descending()
        );

        //데이터베이스에서 게시물 목록 조회
        Page<Post> posts = postRepository.findAll(pageable);

        //게시물 정보만 꺼내기(posts에 다른 정보 있으니)
        List<Post>postList = posts.getContent();

        //게시물 정보를 응답용 DTO의 형태에 맞게 변환
        List<PostDetailResponseDTO> detailList //순서 게시물 정보(해당)
                = postList.stream()
                .map(PostDetailResponseDTO::new) //생성자 참조 문법
                .collect(Collectors.toList());
        //@@

        //DB에서 조회한 정보를 JSON 형태에 맞는 DTO로 변환
        //페이지 구성 정보와 위에 있는 게시물 정보를 또다른 DTO로 한번에 포장해서 리턴할 예정
        //-> PostListResponseDTO

        return PostListResponseDTO.builder()
                .count(detailList.size()) //조회된 게시물 수
                .pageInfo(new PageResponseDTO(posts)) //JPA가 준 페이지 정보가 담긴 객체를 DTO에 전달해서 그쪽에서 알고리즘 돌리게 시킴
                .posts(detailList)
                .build();

    }

}
