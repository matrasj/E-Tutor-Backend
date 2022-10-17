package com.example.etutorbackend.repository;

import com.example.etutorbackend.model.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {

    @Query(value = "SELECT COUNT(*) FROM state, city WHERE city.state_id = state.id\n" +
            "AND state.id = :stateId\n" +
            "GROUP BY state.id", nativeQuery = true)
    int citiesNumberForStateId(@Param("stateId") Long stateId);
}
