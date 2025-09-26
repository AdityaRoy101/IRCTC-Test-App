package com.irctc.app_irctc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainToStationRequest {
    private UUID trainId;
    private UUID stationId;
    private String dayOfWeek;
}
