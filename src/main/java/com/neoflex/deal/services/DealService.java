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

    Long addClient(LoanApplicationRequestDTO loanApplicationRequestDTO);

    void addOffer(LoanOfferDTO loanOfferDTO);

    ScoringDataDTO createScoringDataDTO(FinishRegistrationRequestDTO finishRegistrationRequestDTO, Long applicationId);

    void updateCredit(CreditDTO creditDTO, Long applicationId);

    Application getApplication(Long applicationId);

    void updateApplication(Application application, Status status);

    void updateApplication(Application application);

    Passport savePassport(LoanApplicationRequestDTO loanApplicationRequestDTO);

    Client saveClient(LoanApplicationRequestDTO loanApplicationRequestDTO, Passport passport);

    Employment saveEmployment(FinishRegistrationRequestDTO finishRegistrationRequestDTO);

    ApplicationStatusHistory addApplicationStatusHistory(Status status);

    Application saveApplication(Client client, Status status);

    AddServices addAddServices(LoanOfferDTO loanOfferDTO);

    Credit addCredit(LoanOfferDTO loanOfferDTO, AddServices addServices);


}
