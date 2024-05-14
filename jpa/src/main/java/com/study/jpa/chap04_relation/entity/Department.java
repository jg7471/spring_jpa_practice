package com.study.jpa.chap04_relation.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString(exclude = {"employees"})
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_dept")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dept_id")
    private Long id;

    @Column(name = "dept_name", nullable = false)
    private String name;

    //양방향 맵핑에서는 상대방 엔터티의 갱신에 관여 할 수 없음
    //단순히 읽기 전용(조회)로만 사용해야 함
    @OneToMany(mappedBy = "department") //mappedBy에는 상대방 엔터티의 조인되는 필드명을 작성
    private List<Employee> employees = new ArrayList<>(); //초기화 필요 : (NPE 방지)
    //employees : 테이블엔 실제로 존재하지 않는 컬럼(가상) : 조회만 가능
}
