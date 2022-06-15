package com.neoflex.deal.services;

import com.neoflex.deal.dto.*;
import com.neoflex.deal.models.Application;
import com.neoflex.deal.models.Employment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DealServiceImpl implements DealService {

    ApplicationServiceImpl applicationServiceImpl;

    @Autowired
    public DealServiceImpl(ApplicationServiceImpl applicationServiceImpl) {
        this.applicationServiceImpl = applicationServiceImpl;
    }

    @Override
    public ScoringDataDTO createScoringDataDTO(FinishRegistrationRequestDTO finishRegistrationRequestDTO, Long applicationId) {

        Employment employment = Employment.builder()
                .employmentStatus(finishRegistrationRequestDTO.getEmployment().getEmploymentStatus())
                .employerINN(finishRegistrationRequestDTO.getEmployment().getEmployerINN())
                .salary(finishRegistrationRequestDTO.getEmployment().getSalary())
                .position(finishRegistrationRequestDTO.getEmployment().getPosition())
                .workExperienceTotal(finishRegistrationRequestDTO.getEmployment().getWorkExperienceTotal())
                .workExperienceCurrent(finishRegistrationRequestDTO.getEmployment().getWorkExperienceCurrent())
                .build();

        Application application = applicationServiceImpl.getApplication(applicationId);
        application.getClient().setGender(finishRegistrationRequestDTO.getGenders());
        application.getClient().setMaritalStatus(finishRegistrationRequestDTO.getMaritalStatus());
        application.getClient().setDependentAmount(finishRegistrationRequestDTO.getDependentAmount());
        application.getClient().getPassport().setIssueDate(finishRegistrationRequestDTO.getPassportIssueDate());
        application.getClient().getPassport().setIssueBranch(finishRegistrationRequestDTO.getPassportIssueBrach());
        application.getClient().setEmployment(employment);
        application.getClient().setAccount(finishRegistrationRequestDTO.getAccount());
        applicationServiceImpl.updateApplication(application);
        log.info("createScoringDataDTO() - ScoringDataDTO: Информация о Application обновлена в БД");

        return ScoringDataDTO.builder()
                .amount(application.getCredit().getAmount())
                .term(application.getCredit().getTerm())
                .firstName(application.getClient().getFirstName())
                .lastName(application.getClient().getLastName())
                .middleName(application.getClient().getMiddleName())
                .gender(application.getClient().getGender())
                .birthdate(application.getClient().getBirthdate())
                .passportSeries(application.getClient().getPassport().getSeries())
                .passportNumber(application.getClient().getPassport().getNumber())
                .passportIssueDate(application.getClient().getPassport().getIssueDate())
                .passportIssueBranch(application.getClient().getPassport().getIssueBranch())
                .maritalStatus(application.getClient().getMaritalStatus())
                .dependentAmount(application.getClient().getDependentAmount())
                .employment(finishRegistrationRequestDTO.getEmployment())
                .account(application.getClient().getAccount())
                .isInsuranceEnabled(application.getCredit().getAddServices().getIsInsuranceEnabled())
                .isSalaryClient(application.getCredit().getAddServices().getIsSalaryClient())
                .build();
    }

}
