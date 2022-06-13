package com.neoflex.deal.models.applicationStatusHistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationStatusHistoryRepository extends JpaRepository<ApplicationStatusHistory, Long> {
}
