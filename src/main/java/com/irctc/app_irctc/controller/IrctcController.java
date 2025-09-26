package com.irctc.app_irctc.controller;

import com.irctc.app_irctc.dto.ApiResponse;
import com.irctc.app_irctc.dto.StationRequest;
import com.irctc.app_irctc.dto.TrainRequest;
import com.irctc.app_irctc.dto.TrainToStationRequest;
import com.irctc.app_irctc.entity.TrainMaster;
import com.irctc.app_irctc.service.Impl.IrctcImplementation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class IrctcController {

    private final IrctcImplementation irctcImplementation;

    public IrctcController(
            IrctcImplementation irctcImplementation
    ) {
        this.irctcImplementation = irctcImplementation;
    }

    @PostMapping("/createTrain")
    public Mono<ResponseEntity<ApiResponse>> createTrain(@Valid @RequestBody TrainRequest trainRequest) {

        System.out.println("Create train request camed in" + trainRequest);

        return irctcImplementation.createTrain(trainRequest.getName())
                .flatMap(trainMaster -> {
                    ApiResponse response = ApiResponse.builder()
                            .success(true)
                            .message("Train created")
                            .data(trainMaster)
                            .build();
                    return Mono.just(ResponseEntity.ok(response));
                });
    }

    @PostMapping("/createStation")
    public Mono<ResponseEntity<ApiResponse>> createStation(@Valid @RequestBody StationRequest stationRequest) {

        System.out.println("Create station request camed in" + stationRequest);

        return irctcImplementation.createStation(stationRequest.getName())
                .flatMap(stationMaster -> {
                    ApiResponse response = ApiResponse.builder()
                            .success(true)
                            .message("Station created")
                            .data(stationMaster)
                            .build();
                    return Mono.just(ResponseEntity.ok(response));
                });
    }

    @PostMapping("/mapTrainToStation")
    public Mono<ResponseEntity<ApiResponse>> mapTrainToStation(@Valid @RequestBody TrainToStationRequest trainToStationRequest) {

        return irctcImplementation.mapTrainToStation(
                trainToStationRequest.getTrainId(),
                trainToStationRequest.getStationId(),
                trainToStationRequest.getDayOfWeek()
        )
                .map(train -> ResponseEntity.ok(ApiResponse.builder()
                        .success(true)
                        .message("Mapping created")
                        .data(train)
                        .build()));
    }

    @PostMapping("/find")
    public Mono<ResponseEntity<Map<String, Object>>> findRoutes(@RequestBody Map<String, String> req) {
        String src = req.get("src");
        String dest = req.get("dest");
        String day = req.get("day");

        return irctcImplementation.findRoutes(src, dest, day)
                .collectList()
                .map(routes -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message", "Routes found");
                    response.put("data", routes);
                    return ResponseEntity.ok(response);
                });
    }
}
