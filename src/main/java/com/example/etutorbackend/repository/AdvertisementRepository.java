package com.example.etutorbackend.repository;

import com.example.etutorbackend.model.entity.Advertisement;
import com.example.etutorbackend.model.entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    int countAdvertisementBySubject(@Param("subject")Subject subject);
    Page<Advertisement> findAllByShortDescContaining(@Param("keyphrase") String keyphrase, Pageable pageable);
}
