package com.irctc.app_irctc.repository;

import com.irctc.app_irctc.entity.StationMaster;
import org.neo4j.driver.types.Path;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface RouteRepository extends ReactiveNeo4jRepository<StationMaster, UUID> {

    @Query("""
        MATCH path = (src:Station {name: $src})
                     <-[:STOPS_AT {dayOfWeek: $day}]-(t:Train)
                     -[:STOPS_AT*1..3 {dayOfWeek: $day}]->(dest:Station {name: $dest})
        RETURN path
        """)
    Flux<org.neo4j.driver.types.Path> findRoutesBetweenStations(
            @Param("src") String src,
            @Param("dest") String dest,
            @Param("day") String day
    );
}
