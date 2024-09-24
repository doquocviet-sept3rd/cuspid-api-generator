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
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
import java.util.UUID;

import static java.util.Collections.emptyList;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class Project extends Base {

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

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(nullable = false)
    private Group group = Group.builder().build();

    @ToString.Exclude
    @OneToMany(mappedBy = "project")
    private List<Schema> schemas = emptyList();
}
