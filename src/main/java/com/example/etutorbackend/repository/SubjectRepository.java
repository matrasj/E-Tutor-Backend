package com.example.etutorbackend.repository;

import com.example.etutorbackend.model.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Optional<Subject> findByName(@Param("name") String name);
    @Query(nativeQuery = true, value = "SELECT subject.name , \n" +
            " (SELECT COUNT(advertisement.id) FROM advertisement WHERE advertisement.subject_id = subject.id) AS addsCount\n" +
            "FROM subject\n" +
            "ORDER BY addsCount DESC\n" +
            "LIMIT 15;")
    List<String> findSubjectsOrderByAddsQuantityWithLimit(int limit);

}
