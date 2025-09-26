package com.irctc.app_irctc.service.Impl;

import com.irctc.app_irctc.dto.RouteResponse;
import com.irctc.app_irctc.entity.StationMaster;
import com.irctc.app_irctc.entity.TrainMaster;
import com.irctc.app_irctc.entity.TrainStationMapping;
import com.irctc.app_irctc.repository.RouteRepository;
import com.irctc.app_irctc.repository.StationRepository;
import com.irctc.app_irctc.repository.TrainRepository;
import com.irctc.app_irctc.service.IrctcInterface;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class IrctcImplementation implements IrctcInterface {

    private final TrainRepository trainRepository;
    private final StationRepository stationRepository;
    private final RouteRepository routeRepository;

    public IrctcImplementation(
            TrainRepository trainRepository,
            StationRepository stationRepository,
//            TrainRepository trainRepository
            RouteRepository routeRepository
    ) {
        this.trainRepository = trainRepository;
        this.stationRepository = stationRepository;
        this.routeRepository = routeRepository;
//        this.trainRepository = trainRepository;
    }

    @Override
    @Transactional
    public Mono<TrainMaster> createTrain(String name) {
        TrainMaster trainMaster = TrainMaster.builder()
                .name(name)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        return trainRepository.save(trainMaster)
                .flatMap(train -> {
                    return Mono.just(train);
        });
    }

    @Override
    public Mono<StationMaster> createStation(String name) {
        StationMaster stationMaster = StationMaster.builder()
                .name(name)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        return stationRepository.save(stationMaster)
                .flatMap(station -> {
                    return Mono.just(station);
                });
    }

    @Override
    public Mono<TrainMaster> mapTrainToStation(UUID trainId, UUID stationId, String dayOfWeek) {
        return trainRepository.findById(trainId)
                .zipWith(stationRepository.findById(stationId))
                .flatMap(tuple -> {
                    TrainMaster train = tuple.getT1();
                    StationMaster station = tuple.getT2();

                    TrainStationMapping mapping = TrainStationMapping.builder()
                            .dayOfWeek(dayOfWeek)
                            .station(station)
                            .build();

                    train.getStations().add(mapping);

                    return trainRepository.save(train);
                });
    }

    @Override
    public Flux<RouteResponse> findRoutes(String src, String dest, String day) {
        return routeRepository.findRoutesBetweenStations(src, dest, day)
                .map(path -> {
                    List<String> stations = new ArrayList<>();
                    List<String> trains = new ArrayList<>();

                    path.nodes().forEach(node -> {
                        if (node.hasLabel("Station")) {
                            stations.add(node.get("name").asString());
                        } else if (node.hasLabel("Train")) {
                            trains.add(node.get("name").asString());
                        }
                    });

                    return RouteResponse.builder()
                            .routeId(stations.hashCode())
                            .stations(stations)
                            .trains(trains)
                            .build();
                });
    }
}
