package com.example.etutorbackend.repository;

import com.example.etutorbackend.model.entity.Advertisement;
import com.example.etutorbackend.model.entity.AdvertisementType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    List<Advertisement> findByUserId(@Param("userId") Long userId);

    @Query(value = "SELECT DISTINCT advertisement.id, advertisement.advertisement_type, advertisement.content, advertisement.minutes_duration,\n" +
            "advertisement.price, advertisement.short_desc, advertisement.city_id, advertisement.subject_id, advertisement.user_id,\n" +
            "advertisement.created_at, advertisement.last_updated\n" +
            "FROM advertisement, city, subject, user \n" +
            "WHERE\n" +
            "advertisement.city_id = city.id AND\n" +
            "advertisement.subject_id = subject.id AND\n" +
            "advertisement.user_id = user.id AND\n" +
            "(\n" +
            "advertisement.short_desc LIKE CONCAT('%', :keyphrase, '%') OR\n" +
            "subject.name LIKE CONCAT('%', :keyphrase, '%')\n" +
            ") ORDER BY advertisement.created_at DESC", nativeQuery = true)
    Page<Advertisement> findAdvertisementsByShortDescOrSubjectName(@Param("keyphrase") String keyphrase, Pageable pageable);

    @Query(value = "SELECT DISTINCT advertisement.id, advertisement.advertisement_type, advertisement.content, advertisement.minutes_duration,\n" +
            "advertisement.price, advertisement.short_desc, advertisement.city_id, advertisement.subject_id, advertisement.user_id,\n" +
            "advertisement.created_at, advertisement.last_updated\n" +
            "FROM advertisement, city, subject, user \n" +
            "WHERE\n" +
            "advertisement.city_id = city.id AND\n" +
            "advertisement.subject_id = subject.id AND\n" +
            "advertisement.user_id = user.id AND\n" +
            "(\n" +
            "advertisement.short_desc LIKE CONCAT('%', :keyphrase, '%') OR\n" +
            "subject.name LIKE CONCAT('%', :keyphrase, '%')\n" +
            ")" +
            "AND advertisement.advertisement_type = :advertisementType ORDER BY advertisement.created_at DESC", nativeQuery = true)
    Page<Advertisement> findByShortDescContainingAndAdvertisementType(@Param("keyphrase") String keyphrase,
                                                                      @Param("advertisementType")String advertisementType,
                                                                      Pageable pageable);
    Page<Advertisement> findAllBySubjectName(@Param("subjectName") String subjectName, Pageable pageable);
    Page<Advertisement> findAllByCityName(@Param("cityName") String cityName, Pageable pageable);

    Page<Advertisement> findByAdvertisementType(@Param("advertisementType") AdvertisementType advertisementType, Pageable pageable);


}

