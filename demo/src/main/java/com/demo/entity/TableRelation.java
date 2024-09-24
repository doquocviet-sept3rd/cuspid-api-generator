package com.demo.entity;

import com.demo.entity.enumeration.RelationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class TableRelation extends Base {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false)
    private UUID id;

    @Column(length = 256)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RelationType relationType;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(nullable = false)
    private Field sourceField = Field.builder().build();

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(nullable = false)
    private Field targetField = Field.builder().build();

}
