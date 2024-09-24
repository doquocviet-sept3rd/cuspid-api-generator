package com.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "[group]")
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class Group extends Base {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false)
    private UUID id;

    @Column(nullable = false, length = 8)
    private String name;

    @Column(nullable = false, length = 64)
    private String title;

    @Column(nullable = false, length = 256)
    private String description;

    @ToString.Exclude
    @OneToMany(mappedBy = "group")
    private List<Project> projects = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "group")
    private List<Member> members = new ArrayList<>();

}
