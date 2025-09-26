package com.irctc.app_irctc.repository;

import com.irctc.app_irctc.entity.StationMaster;
import com.irctc.app_irctc.entity.TrainMaster;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface TrainRepository extends ReactiveNeo4jRepository<TrainMaster, UUID> {

    Mono<TrainMaster> findById(UUID trainId);
}
