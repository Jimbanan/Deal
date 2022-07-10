package com.neoflex.deal.services;

import com.neoflex.deal.dto.*;
import com.neoflex.deal.models.Application;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApplicationDTOServiceImpl implements ApplicationDTOService {

    private final ModelMapper modelMapper;
    private final ApplicationServiceImpl applicationServiceImpl;

    public ApplicationDTO getApplicationDTO(Application application) {
        log.info("getApplicationDTO() - ApplicationDTO: получение ApplicationDTO");
        List<ApplicationStatusHistoryDTO> applicationStatusHistoryDTOS = new ArrayList<>();

        for (int i = 0; i < application.getStatusHistory().size(); i++) {
            applicationStatusHistoryDTOS.add(modelMapper.map(application.getStatusHistory().get(i), ApplicationStatusHistoryDTO.class));
        }

        return ApplicationDTO.builder()
                .id(application.getId())
                .clientDTO(ClientDTO.builder()
                        .id(application.getClient().getId())
                        .lastName(application.getClient().getLastName())
                        .firstName(application.getClient().getFirstName())
                        .middleName(application.getClient().getMiddleName())
                        .birthdate(application.getClient().getBirthdate())
                        .email(application.getClient().getEmail())
                        .gender(application.getClient().getGender())
                        .maritalStatus(application.getClient().getMaritalStatus())
                        .dependentAmount(application.getClient().getDependentAmount())
                        .passport(modelMapper.map(application.getClient().getPassport(), PassportDTO.class))
                        .employment(modelMapper.map(application.getClient().getEmployment(), EmploymentDTO.class))
                        .account(application.getClient().getAccount())
                        .build())
                .creditDTO(modelMapper.map(application.getCredit(), CreditDTO.class))
                .status(application.getStatus())
                .creationDate(application.getCreationDate())
                .appliedOffer(application.getAppliedOffer())
                .signDate(application.getSignDate())
                .sesCode(Integer.valueOf(application.getSesCode()))
                .statusHistoryDTO(applicationStatusHistoryDTOS)
                .build();
    }

    public List<ApplicationDTO> getAllApplicationDTO() {
        log.info("getAllApplicationDTO() - List<ApplicationDTO>: получение List<ApplicationDTO>");

        List<ApplicationDTO> applicationDTOS = new ArrayList<>();

        List<Application> allApplication = applicationServiceImpl.getAllApplication();
        log.info("getAllApplicationDTO() - List<ApplicationDTO>: получение списка всех Application в базе данных ");

        for (Application application : allApplication) {
            log.info("getAllApplicationDTO() - List<ApplicationDTO>: Добавление ApplicationDTO в список applicationDTOS");
            applicationDTOS.add(getApplicationDTO(application));
        }
        log.info("getAllApplicationDTO() - List<ApplicationDTO>: Список ApplicationDTO создан");
        return applicationDTOS;
    }

    public ApplicationDTO getApplicationDTOByID(Long applicationId) {
        log.info("getApplicationDTOByID() - ApplicationDTO: получение ApplicationDTO по ID");
        return getApplicationDTO(applicationServiceImpl.getApplication(applicationId));
    }


}
