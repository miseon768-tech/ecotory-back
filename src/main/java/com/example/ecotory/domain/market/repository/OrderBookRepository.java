package com.example.ecotory.domain.market.repository;

import com.example.ecotory.domain.market.entity.Market;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderBookRepository extends JpaRepository<Market, Long> {
    List<Market> findBySymbolIn(List<String> symbols);

}
