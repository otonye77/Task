package com.dot.Dot.Task.scheduler;

import com.dot.Dot.Task.service.serviceinterface.TransferService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TransactionScheduler {

    private final TransferService transferService;

    public TransactionScheduler(TransferService transferService) {
        this.transferService = transferService;
    }

    @Scheduled(cron = "0 0 1 * * ?")
//    @Scheduled(cron = "*/1 * * * * *")
    public void scheduleCommissionCalculation() {
        transferService.calculateCommissions();
    }

    @Scheduled(cron = "0 30 1 * * ?")
//   @Scheduled(cron = "*/1 * * * * *")
    public void scheduleDailySummary() {
        transferService.generateDailySummary(LocalDate.now().minusDays(1));
    }
}
