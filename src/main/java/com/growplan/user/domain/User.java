package com.growplan.user.domain;

import com.growplan.center.domain.Scrap;
import com.growplan.child.domain.UserChild;
import com.growplan.common.BaseEntity;
import com.growplan.common.type.StatusType;
import com.growplan.login.domain.UserSign;
import com.growplan.login.domain.type.SocialLoginType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.growplan.common.type.StatusType.ACTIVE;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = PROTECTED)
@SQLDelete(sql = "UPDATE member SET isValid = 'DELETED' where id = ?")
@SQLRestriction("is_valid = 'ACTIVE'")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, unique = true)
    private String socialLoginId;

    @Column(nullable = false)
    @Enumerated(value = STRING)
    private SocialLoginType socialLoginType;

    @Column(nullable = false)
    @Enumerated(value = STRING)
    private StatusType isValid;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<UserChild> userChildren = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Scrap> scraps = new ArrayList<>();

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    private UserSign userSign;

    public User(
            final String nickname,
            final String email,
            final String socialLoginId,
            final SocialLoginType socialLoginType
    ) {
        this.nickname = nickname;
        this.email = email;
        this.socialLoginId = socialLoginId;
        this.socialLoginType = socialLoginType;
        this.isValid = ACTIVE;
    }

    public User(
            final String email,
            final String socialLoginId,
            final SocialLoginType socialLoginType
    ) {
        this.email = email;
        this.socialLoginId = socialLoginId;
        this.socialLoginType = socialLoginType;
        this.isValid = ACTIVE;
    }

    public void updateUser(
            final String nickname
    ) {
        this.nickname = nickname;
        this.isValid = ACTIVE;
    }
}
