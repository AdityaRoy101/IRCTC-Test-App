package com.irctc.app_irctc.service;

import com.irctc.app_irctc.dto.RouteResponse;
import com.irctc.app_irctc.entity.StationMaster;
import com.irctc.app_irctc.entity.TrainMaster;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface IrctcInterface {
    Mono<TrainMaster> createTrain(String name);
    Mono<StationMaster> createStation(String name);
    Mono<TrainMaster> mapTrainToStation(UUID trainId, UUID stationId, String dayOfWeek);
    Flux<RouteResponse> findRoutes(String src, String dest, String day);
}
