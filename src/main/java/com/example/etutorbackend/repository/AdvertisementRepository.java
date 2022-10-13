package com.example.etutorbackend.repository;

import com.example.etutorbackend.model.entity.Advertisement;
import com.example.etutorbackend.model.entity.AdvertisementType;
import com.example.etutorbackend.model.entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    Page<Advertisement> findAllByShortDescContaining(@Param("keyphrase") String keyphrase, Pageable pageable);
    Page<Advertisement> findAllByShortDescContainingAndAdvertisementType(@Param("keyphrase") String keyphrase,
                                                                         @Param("advertisementType")AdvertisementType advertisementType,
                                                                         Pageable pageable);

    Page<Advertisement> findAllBySubjectName(@Param("subjectName") String subjectName, Pageable pageable);
    Page<Advertisement> findAllByCityName(@Param("cityName") String cityName, Pageable pageable);
}
