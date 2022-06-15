package com.neoflex.deal.services;

import com.neoflex.deal.dto.LoanApplicationRequestDTO;
import com.neoflex.deal.dto.LoanOfferDTO;
import com.neoflex.deal.enums.CreditStatus;
import com.neoflex.deal.enums.Status;
import com.neoflex.deal.models.*;
import com.neoflex.deal.repository.ApplicationRepository;
import com.neoflex.deal.repository.ApplicationStatusHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;

    @Override
    public Long addApplication(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        log.info("addClient() - Long: Добавление клиента");

        Passport passport = Passport.builder()
                .series(loanApplicationRequestDTO.getPassportSeries())
                .number(loanApplicationRequestDTO.getPassportNumber())
                .build();

        Client client = Client.builder()
                .lastName(loanApplicationRequestDTO.getLastName())
                .firstName(loanApplicationRequestDTO.getFirstName())
                .middleName(loanApplicationRequestDTO.getMiddleName())
                .birthdate(loanApplicationRequestDTO.getBirthdate())
                .email(loanApplicationRequestDTO.getEmail())
                .passport(passport)
                .build();

        Application application = Application.builder()
                .client(client)
                .creationDate(LocalDate.now())
                .status(Status.PREAPPROVAL)
                .statusHistory(Arrays.asList(ApplicationStatusHistory.builder()
                        .status(Status.PREAPPROVAL)
                        .time(LocalDateTime.now())
                        .build()))
                .build();

        applicationRepository.save(application);

        return application.getId();
    }

    @Override
    public void addOffer(LoanOfferDTO loanOfferDTO) {
        Application application = getApplication(loanOfferDTO.getApplicationId());

        AddServices addServices = AddServices.builder()
                .isInsuranceEnabled(loanOfferDTO.getIsInsuranceEnabled())
                .isSalaryClient(loanOfferDTO.getIsSalaryClient())
                .build();

        Credit credit = Credit.builder()
                .amount(loanOfferDTO.getRequestedAmount())
                .term(loanOfferDTO.getTerm())
                .monthlyPayment(loanOfferDTO.getMonthlyPayment())
                .rate(loanOfferDTO.getRate())
                .psk(loanOfferDTO.getTotalAmount())
                .addServices(addServices)
                .creditStatus(CreditStatus.CALCULATED)
                .build();

        application.setCredit(credit);
        application.setAppliedOffer(loanOfferDTO.getApplicationId());
        application.setSignDate(LocalDate.now());

        applicationRepository.save(application);
        log.info("addOffer() - void: Информация о выбранном офере добавлена в базу данных");
    }

    public Application getApplication(Long applicationId) {
        log.info("getApplication() - Application: запрос для application.id = {}", applicationId);
        return applicationRepository.findById(applicationId).orElseThrow(()
                -> new NoSuchElementException("with id='" + applicationId + "' does not exist"));
    }

    public void updateApplication(Application application) {
        applicationRepository.save(application);
        log.info("updateApplication() - void: Информация о Application обновлена в БД");
    }

}
