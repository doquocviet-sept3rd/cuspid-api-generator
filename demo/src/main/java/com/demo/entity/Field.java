package com.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.UniqueConstraint;
import java.util.Set;
import java.util.UUID;

import static java.util.Collections.emptySet;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
@jakarta.persistence.Table(uniqueConstraints = {
        @UniqueConstraint(name = "id_order", columnNames = { "id", "order" })
})
public class Field extends Base {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false)
    private UUID id;

    @Column(nullable = false, length = 64)
    private String name;

    @ColumnDefault("0")
    @Column(name = "[order]", nullable = false)
    private Integer order;

    @ManyToOne
    @JoinColumn()
    @ToString.Exclude
    private SqlType sqlType = SqlType.builder().build();

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(nullable = false)
    private DataType dataType = DataType.builder().build();

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(nullable = false)
    private Table table = Table.builder().build();

    @ToString.Exclude
    @OneToMany(mappedBy = "sourceField")
    private Set<TableRelation> tableRelationSources = emptySet();

    @ToString.Exclude
    @OneToMany(mappedBy = "targetField")
    private Set<TableRelation> tableRelationTargets = emptySet();

}
