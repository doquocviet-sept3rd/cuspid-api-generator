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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
@jakarta.persistence.Table(name = "[table]")
public class Table extends Base {

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

    @Column(length = 256)
    private String description;

    @Column(nullable = false)
    private Integer row;

    @ColumnDefault("0")
    @Column(name = "pos_x", nullable = false)
    private Integer posX;

    @ColumnDefault("0")
    @Column(name = "pos_y", nullable = false)
    private Integer posY;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(nullable = false)
    private Schema schema = Schema.builder().build();

    @ToString.Exclude
    @OneToMany(mappedBy = "table")
    private List<Field> fields = new ArrayList<>();

}
