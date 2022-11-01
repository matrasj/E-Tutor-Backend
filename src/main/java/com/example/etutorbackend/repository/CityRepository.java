package com.example.etutorbackend.repository;

import com.example.etutorbackend.model.entity.City;
import com.example.etutorbackend.model.payload.city.CityQuantityPayload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    Optional<City> findByName(@Param("name") String name);
    @Query(nativeQuery = true, value = "SELECT city.name , \n" +
            " (SELECT COUNT(advertisement.id) FROM advertisement WHERE advertisement.city_id = city.id) AS addsCount\n" +
            "FROM city\n" +
            "ORDER BY addsCount DESC\n" +
            "LIMIT :limit")
    List<String> findCitiesOrderByAddsQuantityWithLimit(int limit);

    @Query(value = "SELECT city.name,  (SELECT COUNT(advertisement.id) FROM advertisement WHERE advertisement.city_id = city.id) AS addsQuantity FROM \n" +
            "city, state WHERE city.state_id = state.id AND state.id = :stateId ORDER BY addsQuantity DESC;", nativeQuery = true)
    List<String> findCitiesByStateWithAddsQuantity(@Param("stateId") Long stateId);

    @Query(nativeQuery = true, value = "SELECT city.name , \n" +
            " (SELECT COUNT(advertisement.id) FROM advertisement WHERE advertisement.city_id = city.id) AS addsCount\n" +
            "FROM city\n" +
            "ORDER BY addsCount DESC\n")
    List<String> findAllCitiesWithAddsQuantities();
}
