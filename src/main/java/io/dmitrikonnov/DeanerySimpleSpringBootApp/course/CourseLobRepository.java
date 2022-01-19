package io.dmitrikonnov.DeanerySimpleSpringBootApp.course;

import io.dmitrikonnov.DeanerySimpleSpringBootApp.course.CourseLobs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseLobRepository extends JpaRepository <CourseLobs, Long> {

    @Modifying
    @Query ("update CourseLobs cl set cl.profileImage = ?2 where cl.id = ?1")
    void updateProfileImageById(Long id, byte[] profileImage);
}
