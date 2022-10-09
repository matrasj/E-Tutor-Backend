package com.example.etutorbackend.repository;

import com.example.etutorbackend.model.entity.LessonRange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LessonRangeRepository extends JpaRepository<LessonRange, Long> {
    Optional<LessonRange> findByName(@Param("name") String name);
}
