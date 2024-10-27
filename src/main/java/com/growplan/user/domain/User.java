package com.growplan.user.domain;

import com.growplan.child.domain.UserChild;
import com.growplan.common.BaseEntity;
import com.growplan.common.type.StatusType;
import com.growplan.login.domain.UserSign;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = PROTECTED)
@SQLDelete(sql = "UPDATE member SET isValid = 'DELETED' where id = ?")
@SQLRestriction("status = 'ACTIVE'")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String birthdate;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private StatusType isValid;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<UserChild> userChildren;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    private UserSign userSign;
}
