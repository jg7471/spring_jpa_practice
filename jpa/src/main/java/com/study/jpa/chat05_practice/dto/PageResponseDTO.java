package com.study.jpa.chat05_practice.dto;

import com.study.jpa.chat05_practice.entity.Post;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Page;

@Getter
@Setter
@ToString
public class PageResponseDTO {

    //한 페이지에 배치할 페이지 버튼 수(1 ~ 10 // 11 ~ 20 //...)
    private static final int PAGE_COUNT = 10;
    //JPA가 버튼 알고리즘 돌려주진 x
    private int startPage;
    private int endPage;
    private int currentPage;
    private boolean prev, next;
    private int totalCount;

    //객체 생성 이렇게 -> @builder 노필요
    public PageResponseDTO(Page<Post> pageData) {
        //기존에 사용했던 PageCreator랑 다를게 없음
        //매개값으로 전달된 Page 객체가 기존보다 많은 정보를 제공하기 때문에
        //로직이 좀 더 간편해진 것임.
        this.totalCount = (int) pageData.getTotalElements(); //return long이라 int로 타입 변환
        this.currentPage = pageData.getPageable().getPageNumber() + 1; //-1 했었기에 +1함
        this.endPage = (int) Math.ceil((double) currentPage / PAGE_COUNT) * PAGE_COUNT; //사용자가 17페이지 선택 / 표시 10 / 나머지 7 공식 @@@
        this.startPage = endPage - PAGE_COUNT + 1;

        int realEnd = pageData.getTotalPages();
        if (realEnd < this.endPage) this.endPage = realEnd;

        this.prev = startPage > 1;
        this.next = endPage < realEnd;
    }
}
