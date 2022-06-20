package com.neoflex.deal.services;

import com.neoflex.deal.enums.Status;
import com.neoflex.deal.models.ApplicationStatusHistory;
import com.neoflex.deal.repository.ApplicationStatusHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApplicationStatusHistoryServiceImpl implements ApplicationStatusHistoryService {

    private final ApplicationStatusHistoryRepository applicationStatusHistoryRepository;

    public ApplicationStatusHistory addApplicationStatusHistory(Status status) {
        log.info("addApplicationStatusHistory() - ApplicationStatusHistory: Информация о ApplicationStatusHistory добавлена в БД");
        return applicationStatusHistoryRepository.save(ApplicationStatusHistory.builder()
                .status(status)
                .time(LocalDateTime.now())
                .build());
    }

}
