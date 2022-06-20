package com.neoflex.deal.services;

import com.neoflex.deal.enums.Status;
import com.neoflex.deal.models.ApplicationStatusHistory;

public interface ApplicationStatusHistoryService {

    ApplicationStatusHistory addApplicationStatusHistory(Status status);

}
