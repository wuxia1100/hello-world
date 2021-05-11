package org.cv.sf.repository.jpa;

import org.cv.sf.dto.entity.MUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<MUserEntity, Long> {

    @Query(value = "SELECT * FROM LIN_USER order by id desc   1",nativeQuery = true)
    MUserEntity findOneDesc();

    MUserEntity findByUsername(String username);
}
