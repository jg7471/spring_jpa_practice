package com.study.jpa.chat05_practice.api;

import com.study.jpa.chat05_practice.dto.PageDTO;
import com.study.jpa.chat05_practice.dto.PostListResponseDTO;
import com.study.jpa.chat05_practice.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts") //공통 uri
public class PostApiController {

    private final PostService postService;

    // 리소스: 게시물 (Post)
    /*
        게시물 목록 조회: /posts            - GET, param: (page, size(페이지 출력 게시물 수)
        게시물 개별 조회: /posts/{id}       - GET
        게시물 등록:     /posts            - POST
        게시물 수정:     /posts/{id}       - PATCH
        게시물 삭제:     /posts/{id}       - DELETE
     */


    @GetMapping
    public ResponseEntity<?> list(PageDTO pageDTO) {//@RequestBody : JSON으로 오는 것 // @ResponseBody JSON으로 전달하는것
        //@RestController JSON 전용 Controller
        log.info("/api/v1/posts?page={}&size={}", pageDTO.getPage(), pageDTO.getSize());

        PostListResponseDTO dto = postService.getPosts(pageDTO); //같은거?@@ -> ㅇㅇ

        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detail(@PathVariable Long id) {
        log.info("/api/v1/posts/{}: GET", id);

        return null;
    }
}
