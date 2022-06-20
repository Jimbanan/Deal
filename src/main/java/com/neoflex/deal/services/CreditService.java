package com.neoflex.deal.services;

import com.neoflex.deal.dto.CreditDTO;

public interface CreditService {

    void updateCredit(CreditDTO creditDTO, Long applicationId);

}
