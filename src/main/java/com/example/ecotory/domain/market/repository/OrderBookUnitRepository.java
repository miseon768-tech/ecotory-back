package com.example.ecotory.domain.market.repository;

import com.example.ecotory.domain.market.entity.OrderBookUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderBookUnitRepository extends JpaRepository<OrderBookUnit, Long> {

}
