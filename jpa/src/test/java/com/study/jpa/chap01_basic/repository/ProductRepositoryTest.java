package com.study.jpa.chap01_basic.repository;

import com.study.jpa.chap01_basic.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional //jpa는 트랜잭션 단위로 동작하기 때문에 설정//스프링꺼
@Rollback(false) //테스트 클래스에서 트랜잭션을 사용하면 Rollback이 자동으로 됨 -> Rollback 막기. :데이터가 들어가는거 봐야하니까
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    //tdd
    @Test
    @DisplayName("데이터베이스에 상품을 저장해야 한다.")
    void testSave() {
        // given
        Product p = Product.builder()
                .name("구두")
                .price(500000)
                .category(Product.Category.FASHION)
                .build();

        // when
        Product saved = productRepository.save(p);

        // then
        assertNotNull(saved); //null이 아닐 것(데이터 잘 들어갈 것)단언한다
    }

    @Test
    @DisplayName("id가 2번인 데이터를 삭제해야 한다")
    void testRemove() {
        // given
        Long id = 2L;

        // when
        productRepository.deleteById(id);

        // then
    }

    @Test
    @DisplayName("상품 전체조회를 하면 상품의 개수는 2개여야 한다.")
    void testFindAll() {
        // given

        // when
        List<Product> products = productRepository.findAll();

        // then
        products.forEach(System.out::println);
        assertEquals(2,products.size());

    }

    @Test
    @DisplayName("3번 상품을 조회하면 상품명이 구두일 것이다")
    void testFindOne() {
        // given
        Long id = 3L;

        // when
        //Optional<T>: NPE(null point exception)방지 할 수 있도록 타입으로 강제
        //null 값을 객체로 감싸서 바로 확인하지 못하게 보호, 후속 작업들을 메서드로 다양하게 제공
        Optional<Product> product = productRepository.findById(id); //null 체크

        // then
        //만약 product에 값이 존재한다면(ifPresent() 메서드), 해당 값에 대해 주어진 람다 표현식을 실행하고, 이 표현식은 상품의 이름이 "구두"인지를 확인
        product.ifPresent(p -> {
            assertEquals("구두", p.getName()); //단언1
        });

        //product에서 값을 추출하여 foundProduct 변수에 할당합니다. 만약 product에 값이 없다면(즉, Optional이 비어있다면), NoSuchElementException을 발생시킵니다.
        Product foundProduct = product.orElseThrow();//예외처리
        assertNotNull(foundProduct); //단언2

        System.out.println("foundProduct = " + foundProduct);
    }

    @Test
    @DisplayName("1번 상품의 이름과 가격을 변경해야 한다.")
    void testModify() {
        // given
        Long id = 1L;
        String newName = "자장면";
        int newPrice = 6000;
        Product.Category newCategory = Product.Category.FOOD;

        // when

        /*
        * jpa에서는 update는 따로 메서드로 제공되지 않고
        * 조회한 후에 setter로 변경하면 자동으로 update문이 나감
        * 변경한 후 save 호출 -> 그럼 update가 됨
        * */
        Optional<Product> product = productRepository.findById(id);
        product.ifPresent(p -> {
            p.setName(newName);
            p.setPrice(newPrice);
            p.setCategory(newCategory);

            productRepository.save(p);
        });

        // then
    }
}














