package com.study.jpa.chat05_practice.api;

import com.study.jpa.chat05_practice.dto.*;
import com.study.jpa.chat05_practice.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Tag(name = "post API", description = "게시물 조회, 등록 및 수정, 삭제 api 입니다.")
@RestController//REST 전용
@Slf4j
@RequiredArgsConstructor //private final PostService postService;
@RequestMapping("/api/v1/posts") //공통 uri
public class PostApiController {

    private final PostService postService;

    // 리소스: 게시물 (Post)
    /*
        게시물 목록 조회: /posts            - GET, param: (page, size(페이지 출력 게시물 수)
        게시물 개별 조회: /posts/{id}       - GET
        게시물 등록:     /posts            - POST, payload : (writer, title, content, hashTags) <- JSON data
        게시물 수정:     /posts       - PATCH
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
    public ResponseEntity<?> detail(@PathVariable("id") Long id) { //id같으면 생략 가능, requestparm 동
        log.info("/api/v1/posts/{}: GET", id);

        try {
            PostDetailResponseDTO dto = postService.getDetail(id);
            return ResponseEntity.ok().body(dto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //게시물 등록
    @Operation(summary = "게시물 등록", description = "게시물 작성 및 등록을 담당하는 메서드.")
    @Parameters({
            @Parameter(name = "writer", description = "게시물의 작성자 이름을 쓰세요!", example = "김뽀삐", required = true),
            @Parameter(name = "title", description = "게시물의 제목을 쓰세요!", example = "제목제목", required = true),
            @Parameter(name = "content", description = "게시물의 내용을 쓰세요!", example = "내용내용"),
            @Parameter(name = "hashTags", description = "게시물의 해시태그를 작성하세요!", example = "['하하', '호호']")
    })
    @PostMapping
    public ResponseEntity<?> create(
            @Validated @RequestBody PostCreateDTO dto,
            BindingResult result //검증 에러 정보를 가진 객체 담음
    ) {
        log.info("/api/v1/posts POST!! - payload: {}", dto);

        if (dto == null) {
            return ResponseEntity
                    .badRequest()
                    .body("등록 게시물 정보를 전달해 주세요!");
        }
        ResponseEntity<List<FieldError>> fieldErrors = getValidatedResult(result);
        if (fieldErrors != null) return fieldErrors;

        // 위에 존재하는 if문을 모두 건너뜀 -> dto가 null도 아니고, 입력값 검증도 모두 통과함 -> service에게 명령.
        try {
            PostDetailResponseDTO responseDTO = postService.insert(dto);
            return ResponseEntity
                    .ok()
                    .body(responseDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .internalServerError()
                    .body("서버 에러 원인: " + e.getMessage());
        }
    }

    // 입력값 검증(Validation)의 결과를 처리해 주는 전역 메서드
    private static ResponseEntity<List<FieldError>> getValidatedResult(BindingResult result) {
        if (result.hasErrors()) { // 입력값 검증 단계에서 문제가 있었다면 true
            List<FieldError> fieldErrors = result.getFieldErrors();
            fieldErrors.forEach(err -> {
                log.warn("invalid client data - {}", err.toString());
            });
            return ResponseEntity
                    .badRequest()
                    .body(fieldErrors);
        }
        return null;
    }

    //게시물 수정
    @Operation(summary = "게시글 수정", description = "게시물 수정을 담당하는 메서드 입니다.")
    @ApiResponses(value = {//응답 여러종류+s
            @ApiResponse(responseCode = "200", description = "수정 완료!",
                    content = @Content(schema = @Schema(implementation = PostDetailResponseDTO.class))),
            //컨텐츠의 스키마의 DTO모양으로 JSON형태로 리턴
            @ApiResponse(responseCode = "400", description = "입력값 검증 실패"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND")

    })
    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH}) //PUT, PATCH 둘 다 처리
    public ResponseEntity<?> update(
            @Validated @RequestBody PostModifyDTO dto,
            BindingResult result, //검증/JSON
            HttpServletRequest request //요청방식 알기
    ) {
        log.info("/api/v1/post {} - payload: {}", request.getMethod(), dto);

        ResponseEntity<List<FieldError>> fieldErrors = getValidatedResult(result);
        if (fieldErrors != null) return fieldErrors;

        //메서드 추출 ctrl alt M
        PostDetailResponseDTO responseDTO = postService.modify(dto);

        return ResponseEntity.ok().body(responseDTO);
    }

    //게시물 삭제
    //테스트 진행 시 해시태그가 없는 글을 삭제 해 보세요
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        log.info("/api/v1/posts/{} DELETE!", id);
        postService.delete(id);

        //FK 있으면 삭제 불가
        //HashTag : @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
        //같이 지워주세여(FK 있을때) : ON DELETE CASCADE 추가됨!!!

        return ResponseEntity.ok("delSuccess!");
    }

}