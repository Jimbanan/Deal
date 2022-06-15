package com.neoflex.deal.services;

import com.neoflex.deal.dto.CreditDTO;
import com.neoflex.deal.dto.LoanApplicationRequestDTO;
import com.neoflex.deal.dto.LoanOfferDTO;
import com.neoflex.deal.dto.PaymentScheduleElement;
import com.neoflex.deal.enums.Genders;
import com.neoflex.deal.enums.Status;
import com.neoflex.deal.models.Application;
import com.neoflex.deal.models.Client;
import com.neoflex.deal.models.Passport;
import com.neoflex.deal.models.PaymentSchedule;
import com.neoflex.deal.repository.ApplicationRepository;
import com.neoflex.deal.repository.ApplicationStatusHistoryRepository;
import com.neoflex.deal.repository.PassportRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import javax.transaction.Transactional;

import java.math.BigDecimal;

@Transactional
@SpringBootTest
class ApplicationServiceImplTest {

    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private ApplicationStatusHistoryRepository applicationStatusHistoryRepository;
    @Autowired
    private PassportRepository passportRepository;

    @InjectMocks
    private ApplicationStatusHistoryServiceImpl applicationStatusHistoryServiceImpl;

    @InjectMocks
    private ApplicationServiceImpl applicationServiceImpl;

    @Test
    void addApplication() {
        ReflectionTestUtils.setField(applicationStatusHistoryServiceImpl, "applicationStatusHistoryRepository", applicationStatusHistoryRepository);

        ReflectionTestUtils.setField(applicationServiceImpl, "applicationStatusHistoryServiceImpl", applicationStatusHistoryServiceImpl);
        ReflectionTestUtils.setField(applicationServiceImpl, "applicationRepository", applicationRepository);

        Long aLong = applicationServiceImpl.addApplication(LoanApplicationRequestDTO.builder().build());
        Assertions.assertNotNull(aLong);

    }

    @Test
    void addApplicationOffer() {
        ReflectionTestUtils.setField(applicationStatusHistoryServiceImpl, "applicationStatusHistoryRepository", applicationStatusHistoryRepository);

        ReflectionTestUtils.setField(applicationServiceImpl, "applicationStatusHistoryServiceImpl", applicationStatusHistoryServiceImpl);
        ReflectionTestUtils.setField(applicationServiceImpl, "applicationRepository", applicationRepository);

        Long id = applicationRepository.findTopByOrderByIdDesc().getId();

        applicationServiceImpl.addApplicationOffer(LoanOfferDTO.builder().applicationId(id)
                .requestedAmount(BigDecimal.valueOf(149999))
                .totalAmount(BigDecimal.valueOf(140000))
                .term(6)
                .monthlyPayment(BigDecimal.valueOf(14000))
                .rate(BigDecimal.valueOf(14))
                .isInsuranceEnabled(true)
                .isSalaryClient(true)
                .build());

        Application application = applicationServiceImpl.getApplication(id);

        Assertions.assertNotNull(application.getCredit().getAddServices().getIsInsuranceEnabled());
        Assertions.assertNotNull(application.getCredit().getAddServices().getIsSalaryClient());
        Assertions.assertNotNull(application.getCredit().getAmount());
        Assertions.assertNotNull(application.getCredit().getTerm());
        Assertions.assertNotNull(application.getCredit().getMonthlyPayment());
        Assertions.assertNotNull(application.getCredit().getRate());
        Assertions.assertNotNull(application.getCredit().getPsk());
        Assertions.assertNotNull(application.getCredit().getCreditStatus());

    }

    @Test
    void getApplication() {
        ReflectionTestUtils.setField(applicationStatusHistoryServiceImpl, "applicationStatusHistoryRepository", applicationStatusHistoryRepository);

        ReflectionTestUtils.setField(applicationServiceImpl, "applicationStatusHistoryServiceImpl", applicationStatusHistoryServiceImpl);
        ReflectionTestUtils.setField(applicationServiceImpl, "applicationRepository", applicationRepository);
        Long id = applicationRepository.findTopByOrderByIdDesc().getId();
        Application application = applicationServiceImpl.getApplication(id);
        Assertions.assertNotNull(application);
    }

    @Test
    void updateApplication() {
        ReflectionTestUtils.setField(applicationStatusHistoryServiceImpl, "applicationStatusHistoryRepository", applicationStatusHistoryRepository);

        ReflectionTestUtils.setField(applicationServiceImpl, "applicationStatusHistoryServiceImpl", applicationStatusHistoryServiceImpl);
        ReflectionTestUtils.setField(applicationServiceImpl, "applicationRepository", applicationRepository);

        Application application = applicationRepository.findTopByOrderByIdDesc();
        Client client = Client.builder()
                .gender(Genders.NOT_BINARY)
                .passport(Passport.builder().id(passportRepository.findTopByOrderByIdDesc().getId() + 1).build())
                .build();

        application.setClient(client);
        applicationServiceImpl.updateApplication(application);
        Application applicationTest = applicationRepository.findTopByOrderByIdDesc();
        Assertions.assertNotNull(applicationTest.getClient().getGender());
    }

}