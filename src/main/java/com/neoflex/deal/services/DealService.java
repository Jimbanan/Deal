package com.neoflex.deal.services;

import com.neoflex.deal.dto.*;
import com.neoflex.deal.enums.Status;
import com.neoflex.deal.models.AddServices;
import com.neoflex.deal.models.Application;
import com.neoflex.deal.models.ApplicationStatusHistory;
import com.neoflex.deal.models.Client;
import com.neoflex.deal.models.Credit;
import com.neoflex.deal.models.Employment;
import com.neoflex.deal.models.Passport;

public interface DealService {

    ScoringDataDTO createScoringDataDTO(FinishRegistrationRequestDTO finishRegistrationRequestDTO, Long applicationId);

    void updateCredit(CreditDTO creditDTO, Long applicationId);

    Application getApplication(Long applicationId);

    void updateApplication(Application application, Status status);

    Employment saveEmployment(FinishRegistrationRequestDTO finishRegistrationRequestDTO);

    ApplicationStatusHistory addApplicationStatusHistory(Status status);


}
