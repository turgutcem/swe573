package com.swe573.swe573.repo;

import com.swe573.swe573.model.PasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PasswordTokenRepository extends JpaRepository<PasswordToken,Long> {

    Optional<PasswordToken> findByToken(String token);
    @Transactional
    @Modifying
    @Query("UPDATE PasswordToken c " +
            "SET c.confirmedAt = ?2 " +
            "WHERE c.token = ?1")
    int updateConfirmedAt(String token,
                          LocalDateTime confirmedAt);
}
