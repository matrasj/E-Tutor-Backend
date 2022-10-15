package com.example.etutorbackend.repository;

import com.example.etutorbackend.model.entity.Advertisement;
import com.example.etutorbackend.model.entity.AdvertisementType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    List<Advertisement> findByUserId(@Param("userId") Long userId);
    Page<Advertisement> findAllByShortDescOrSubjectContaining(@Param("keyphrase1") String keyphrase1,@Param("keyphrase2") String keyphrase2, Pageable pageable);
    Page<Advertisement> findByShortDescContainingOrAdvertisementType(@Param("keyphrase1") String keyphrase1,
                                                                     @Param("advertisementType")AdvertisementType advertisementType,
                                                                     Pageable pageable);

    Page<Advertisement> findAllBySubjectName(@Param("subjectName") String subjectName, Pageable pageable);
    Page<Advertisement> findAllByCityName(@Param("cityName") String cityName, Pageable pageable);
}

