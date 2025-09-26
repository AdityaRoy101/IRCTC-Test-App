package com.irctc.app_irctc.entity;

import lombok.*;
import org.springframework.data.neo4j.core.schema.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RelationshipProperties
public class TrainStationMapping {

    @Id
    @GeneratedValue
    private Long id;

    private String dayOfWeek; // e.g. MONDAY, TUESDAY

    @TargetNode
    private StationMaster station;
}
