package com.capgemini.telecom.ordersystem.service;

import com.capgemini.telecom.ordersystem.enums.SimCardStatus;
import com.capgemini.telecom.ordersystem.model.SimCard;
import com.capgemini.telecom.ordersystem.model.SimPurchaseLog;
import com.capgemini.telecom.ordersystem.repository.SimCardRepository;
import com.capgemini.telecom.ordersystem.repository.SimPurchaseLogRepository;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SimPurchaseLogService {

    @Autowired
    private SimCardRepository simCardRepository;

    @Autowired
    private SimPurchaseLogRepository simPurchaseLogRepository;

    @Scheduled(cron = "0 11 14 * * ?") // Runs at 11:45 PM every day
    @SchedulerLock(name = "uniqueTaskName", lockAtMostFor = "5m", lockAtLeastFor = "2s")
    public void calculateDailySimCardPurchases() {
        ZoneId chicagoZone = ZoneId.of("America/Chicago");
        LocalDate today = LocalDate.now(chicagoZone);
        LocalDateTime startTime = today.atStartOfDay(chicagoZone).toLocalDateTime();
        LocalDateTime endTime = today.atTime(LocalTime.MAX).atZone(chicagoZone).toLocalDateTime();
        log.info("start : " + startTime + " end : " + endTime);
        List<SimCard> dailyPurchases = simCardRepository.findByPurchaseDateBetween(startTime, endTime);
        Map<SimCardStatus, Long> statusCounts = dailyPurchases.stream()
                .collect(Collectors.groupingBy(SimCard::getStatus, Collectors.counting()));

        SimPurchaseLog simPurchaseLog = new SimPurchaseLog();
        simPurchaseLog.setPurchaseDate(today);
        simPurchaseLog.setStatusCounts(statusCounts);
        simPurchaseLogRepository.save(simPurchaseLog);

        log.info("Daily SIM card purchase log saved for: " + today);
        log.info("Status Counts: " + statusCounts);

    }
}
