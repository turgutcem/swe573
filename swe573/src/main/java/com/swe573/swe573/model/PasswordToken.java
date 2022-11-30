package com.swe573.swe573.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PasswordToken {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String token;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime expiresAt;
    private LocalDateTime confirmedAt;
    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "user_id"
    )
    private User user;

    public PasswordToken(String token,
                         LocalDateTime createdAt,
                         LocalDateTime expiresAt,
                         User user){
        this.token=token;
        this.createdAt=createdAt;
        this.expiresAt=expiresAt;
        this.user=user;
    }
}
