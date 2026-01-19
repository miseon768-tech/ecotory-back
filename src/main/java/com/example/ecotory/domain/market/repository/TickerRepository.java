package com.example.ecotory.domain.market.repository;

import com.example.ecotory.domain.market.entity.Ticker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TickerRepository extends JpaRepository<Ticker, Long> {

}
