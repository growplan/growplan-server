package com.growplan.center.domain;

import com.growplan.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = PROTECTED)
public class Center {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @OneToMany(mappedBy = "center", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<CenterTag> centerTags = new HashSet<>();

    @OneToMany(mappedBy = "center", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Scrap> scraps = new ArrayList<>();
}
