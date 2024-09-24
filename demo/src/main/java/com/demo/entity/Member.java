package com.demo.entity;

import com.demo.entity.composite_key.MemberPK;
import com.demo.entity.enumeration.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class Member extends Base {

    @EmbeddedId
    private MemberPK id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role = Role.GROUP_DEPUTY;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(nullable = false, insertable = false, updatable = false)
    private User user = User.builder().build();

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(nullable = false, insertable = false, updatable = false)
    private Group group = Group.builder().build();

}
