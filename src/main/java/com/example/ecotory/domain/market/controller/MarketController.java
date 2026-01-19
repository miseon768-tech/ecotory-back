package com.example.ecotory.domain.market.controller;

import com.example.ecotory.domain.market.service.MarketService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "")
@Tag(name = "", description = "")
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/market")
public class MarketController {

    private final MarketService marketService;

   // 마켓 전체 조회



    }


