package com.demo.entity.composite_key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.UUID;

@Data
@Embeddable
@SuperBuilder
@NoArgsConstructor
public class MemberPK implements Serializable {

    @Column(nullable = false)
    private UUID groupId;

    @Column(nullable = false)
    private UUID userId;
}
