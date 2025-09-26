package com.irctc.app_irctc.repository;

import com.irctc.app_irctc.entity.StationMaster;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface StationRepository extends ReactiveNeo4jRepository<StationMaster, UUID> {

    Mono<StationMaster> findById(String id);
}
