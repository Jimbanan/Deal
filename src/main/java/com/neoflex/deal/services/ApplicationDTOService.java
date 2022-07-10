package com.neoflex.deal.services;

import com.neoflex.deal.dto.ApplicationDTO;
import com.neoflex.deal.models.Application;

import java.util.List;

public interface ApplicationDTOService {

    ApplicationDTO getApplicationDTO(Application application);

    List<ApplicationDTO> getAllApplicationDTO();

    ApplicationDTO getApplicationDTOByID(Long applicationId);

}
