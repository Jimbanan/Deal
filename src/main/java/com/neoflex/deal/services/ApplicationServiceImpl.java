package com.neoflex.deal.services;

import com.neoflex.deal.dto.LoanApplicationRequestDTO;
import com.neoflex.deal.dto.LoanOfferDTO;
import com.neoflex.deal.enums.CreditStatus;
import com.neoflex.deal.enums.Status;
import com.neoflex.deal.models.*;
import com.neoflex.deal.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ApplicationStatusHistoryServiceImpl applicationStatusHistoryServiceImpl;

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
    public void addApplicationOffer(LoanOfferDTO loanOfferDTO) {

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
        application.setAppliedOffer(loanOfferDTO.toString());
        application.setSignDate(LocalDate.now());

        Integer min = 100000;
        Integer max = 999999;
        Integer sesCode = (int) (Math.random() * ++max) + min;

        application.setSesCode(sesCode.toString());

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


    @Transactional
    public void updateApplication(Application application, Status status) {
        List<ApplicationStatusHistory> list = new ArrayList<>();
        list.add(applicationStatusHistoryServiceImpl.addApplicationStatusHistory(status));

        application.getStatusHistory().add(list.get(0));
        log.info("updateApplication() - void: Информация о application.statusHistory добавлена");

        applicationRepository.save(application);
        log.info("updateApplication() - void: Информация о Application обновлена в БД");
    }

}