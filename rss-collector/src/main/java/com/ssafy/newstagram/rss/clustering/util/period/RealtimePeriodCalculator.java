package com.ssafy.newstagram.rss.clustering.util.period;

import java.time.LocalDateTime;

public class RealtimePeriodCalculator implements PeriodCalculator {

    @Override
    public LocalDateTime getStart() {
        return LocalDateTime.now().minusHours(6);
    }

    @Override
    public LocalDateTime getEnd() {
        return LocalDateTime.now();
    }
}
