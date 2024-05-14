package com.study.jpa.chap04_relation.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.util.Lazy;

@Setter
@Getter
//JPA 연관관계 맵핑에서 연관관계 데이터는 toString에서 제외해야 함(순환 참조 발생)
@ToString(exclude = {"department"})
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_emp")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id")
    private Long id;

    @Column(name = "dept_name", nullable = false)
    private String name;

    //EAGER : 항상 무조건 조인(join) 수행
    //LAZY : 필요한 경우에만 조인 수행(실무 多)
    @ManyToOne(fetch = FetchType.LAZY) //내 기준 : 단방향 맵핑 : 갱신 관여 可 ManyToOne 갱신 가능 <> OneToMany
    @JoinColumn(name = "dept_id") //조인되는 컬럼명
    private Department department;

}
