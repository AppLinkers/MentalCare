package com.example.mentalCare.consultant.profile.repository;

import com.example.mentalCare.consultant.profile.domain.Consultant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ConsultantRepository extends JpaRepository<Consultant, Long> {

    @Query("select c from Consultant c, User u where c.user.id = u.id and u.login_id = :userLoginId")
    Consultant findByUserLogin_id(String userLoginId);
}
