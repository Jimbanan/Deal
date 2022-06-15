package com.neoflex.deal.services;

import com.neoflex.deal.dto.LoanApplicationRequestDTO;
import com.neoflex.deal.dto.LoanOfferDTO;
import com.neoflex.deal.models.Application;

public interface ApplicationService {

    Long addApplication(LoanApplicationRequestDTO loanApplicationRequestDTO);

    void addOffer(LoanOfferDTO loanOfferDTO);

    Application getApplication(Long applicationId);

    void updateApplication(Application application);
}
