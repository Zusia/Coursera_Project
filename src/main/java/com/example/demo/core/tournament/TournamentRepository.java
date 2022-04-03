package com.example.demo.core.tournament;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long>{
    Optional<Object> findById(Long id);
}
