package com.neoflex.deal.services;

import com.neoflex.deal.dto.*;

public interface DealService {

    ScoringDataDTO createScoringDataDTO(FinishRegistrationRequestDTO finishRegistrationRequestDTO, Long applicationId);

}
