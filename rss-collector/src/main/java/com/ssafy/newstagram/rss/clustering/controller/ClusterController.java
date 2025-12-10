package com.ssafy.newstagram.rss.clustering.controller;

import com.ssafy.newstagram.rss.clustering.service.ClusteringService;
import com.ssafy.newstagram.rss.clustering.util.period.Period;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ClusterController {

    private final ClusteringService clusteringService;

    // REALTIME, DAILY, WEEKLY
    @GetMapping("/clustering/{periodType}")
    public String clustering(@PathVariable("periodType") String periodType) {
        Period period = Period.valueOf(periodType);
        clusteringService.clusteringByPeriodEnum(period);
        return "클러스터링 완료";
    }

}
