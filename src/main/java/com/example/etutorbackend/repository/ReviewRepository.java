package com.example.etutorbackend.repository;

import com.example.etutorbackend.model.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByAdvertisementId(@Param("advertisementId") Long advertisementId);
}
