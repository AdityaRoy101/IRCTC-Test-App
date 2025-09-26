package com.irctc.app_irctc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.neo4j.core.schema.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Node("Train")
public class TrainMaster {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder.Default
    @Property("is_active")
    private Boolean isActive = true;

    @Relationship(type = "STOPS_AT", direction = Relationship.Direction.OUTGOING)
    private List<TrainStationMapping> stations = new ArrayList<>();
}
