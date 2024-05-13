package com.study.jpa.chap01_basic.entity;

import jakarta.persistence.*;
import jdk.jfr.Category;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Table(name = "tbl_product") //테이블이름 커스텀
@Entity //이거 entity야 : 클래스이름 -> 테이블명(딱히 언급 없을시 <> @Table)
public class Product {

    @Id //Primary key 세팅
    @GeneratedValue(strategy = GenerationType.IDENTITY) //전략 다양함
    @Column(name = "prod_id") //테이블 생성시 id
    private Long id; //wrapper 타입 : 기본타입 null x, 객체타입 null o

    @Column(name ="prod_name", nullable = false, length = 30) //prod_name으로 이름 변경, not null
    private String name;

    private int price;

    @Enumerated(EnumType.STRING) //ori 면 숫자로 저장됨
    private Category category;

    @CreationTimestamp
    @Column(updatable = false) //수정 불가
    private LocalDateTime createDate;

    @UpdateTimestamp
    private LocalDateTime updateDate;

    public enum Category{ //enum 세팅
        FOOD, FASHION, ELECTRONIC
    }


}
