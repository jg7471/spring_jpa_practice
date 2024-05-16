package com.study.jpa.chat05_practice.repository;

import com.study.jpa.chat05_practice.entity.HashTag;
import com.study.jpa.chat05_practice.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashTagRepository extends JpaRepository<HashTag, Long> {//타입 HashTag
}
