package com.study.jpa.chap02_querymethod.repository;

import com.study.jpa.chap02_querymethod.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, String> {

    //쿼리메서드 규칙 : 반자동
    List<Student> findByName(String name); //메서드 이름이 쿼리문 결정
    List<Student> findByCityAndMajor(String city, String major);
    List<Student>findByMajorContaining(String major); //Containing : like %수학%

    //네이티브 쿼리 사용 : 수동(최후의 수단)
    @Query(value = "SELECT * FROM tbl_student WHERE stu_name = :nm", nativeQuery = true)
    List<Student> findNameWithSQL(@Param("nm") String name);

    //native-sql(기존 sql)
    //SELECT 컬럼명 FROM 테이블명
    //WHERE 컬럼 =?

    //JPQL : entity 위주로 : 核心!!!
    // 쿼리 작성
    //SELECT 별칭 FROM 엔터티클래스명 AS 별칭
    //WHERE 별칭.필드명 = ?
    //SELECT st FROM Student AS st
    //WHERE st.name = ?

    //도시 이름으로 학생 조회
    //@Query(value = "SELECT * FROM tbl_student WHERE city= ?1", nativeQuery = true) //?1 첫번째 파라미터 or 직접 이름 : city
    @Query("SELECT s FROM Student s WHERE s.city = ?1") //(entity)student = s
    List<Student> getByCityWithJPQL(String city); //도시 중복되니 List로 받음

    @Query("SELECT s FROM Student s WHERE s.name LIKE %:nm%") //클래스명 o , 컬럼명 x(SQL)
    List<Student> searchByNameWithJPQL(@Param("nm") String name);


    //JPQL로 수정 삭제 쿼리 쓰기
    @Modifying //조회가 아닐 경우 무조건 붙여야 함
    @Query("DELETE FROM Student s WHERE s.name = ?1")
    void deleteByNameWithJPQL(String name);
}
