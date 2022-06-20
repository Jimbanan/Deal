package com.neoflex.deal.services;

import com.neoflex.deal.dto.CreditDTO;
import com.neoflex.deal.dto.PaymentScheduleElement;
import com.neoflex.deal.enums.CreditStatus;
import com.neoflex.deal.enums.Status;
import com.neoflex.deal.models.*;
import com.neoflex.deal.repository.ApplicationRepository;
import com.neoflex.deal.repository.PaymentScheduleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Transactional
@SpringBootTest
class CreditServiceImplTest {

    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private PaymentScheduleRepository paymentScheduleRepository;
    @Autowired
    private ApplicationStatusHistoryServiceImpl applicationStatusHistoryServiceImpl;

    @InjectMocks
    private final ApplicationServiceImpl applicationServiceImpl;
    @InjectMocks
    private final PaymentScheduleServiceImpl paymentScheduleImpl;

    @Autowired
    CreditServiceImplTest(ApplicationServiceImpl applicationServiceImpl,
                          PaymentScheduleServiceImpl paymentScheduleImpl) {
        this.applicationServiceImpl = applicationServiceImpl;
        this.paymentScheduleImpl = paymentScheduleImpl;
    }

    @InjectMocks
    private CreditServiceImpl creditServiceImpl;

    @BeforeEach
    void createReflection() {
        ReflectionTestUtils.setField(applicationServiceImpl, "applicationStatusHistoryServiceImpl", applicationStatusHistoryServiceImpl);
        ReflectionTestUtils.setField(applicationServiceImpl, "applicationRepository", applicationRepository);

        ReflectionTestUtils.setField(paymentScheduleImpl, "paymentScheduleRepository", paymentScheduleRepository);

        ReflectionTestUtils.setField(creditServiceImpl, "applicationServiceImpl", applicationServiceImpl);
        ReflectionTestUtils.setField(creditServiceImpl, "paymentScheduleImpl", paymentScheduleImpl);
        createApplication();
    }


    void createApplication() {
        List<ApplicationStatusHistory> list = Arrays.asList(ApplicationStatusHistory.builder()
                .status(Status.PREAPPROVAL)
                .time(LocalDateTime.now())
                .build());

        List<ApplicationStatusHistory> arraylist = new ArrayList<>(list);

        applicationRepository.save(Application.builder()
                .client(Client.builder()
                        .birthdate(LocalDate.of(1980, 9, 26))
                        .dependentAmount(1)
                        .email("usususu@mail.ru")
                        .firstName("Test")
                        .gender(null)
                        .maritalStatus(null)
                        .dependentAmount(null)
                        .lastName("Test")
                        .middleName("Test")
                        .passport(Passport.builder()
                                .number("123456")
                                .series("1234")
                                .issueDate(null)
                                .issueBranch(null)
                                .build())
                        .employment(null)
                        .account(null)
                        .build())
                .credit(Credit.builder()
                        .amount(BigDecimal.valueOf(56202.00))
                        .term(20)
                        .monthlyPayment(BigDecimal.valueOf(3327.72))
                        .rate(BigDecimal.valueOf(20))
                        .psk(BigDecimal.valueOf(66554.41))
                        .addServices(AddServices.builder()
                                .isInsuranceEnabled(true)
                                .isSalaryClient(true)
                                .build())
                        .paymentSchedule(Arrays.asList())
                        .creditStatus(CreditStatus.CALCULATED)
                        .build())
                .status(Status.PREAPPROVAL)
                .creationDate(LocalDate.now())
                .appliedOffer(null)
                .signDate(LocalDate.now())
                .sesCode(null)
                .statusHistory(arraylist)
                .build());

    }

    @Test
    void updateCredit() {
        Long countPaymentScheduleRepository = paymentScheduleRepository.count();

        Long id = applicationRepository.findTopByOrderByIdDesc().getId();

        creditServiceImpl.updateCredit(CreditDTO.builder()
                .amount(BigDecimal.valueOf(100000))
                .term(6)
                .monthlyPayment(BigDecimal.valueOf(100000))
                .rate(BigDecimal.valueOf(10))
                .psk(BigDecimal.valueOf(10))
                .isInsuranceEnabled(true)
                .isSalaryClient(true)
                .paymentSchedule(Arrays.asList(PaymentScheduleElement.builder()
                        .number(1)
                        .date(LocalDate.now())
                        .totalPayment(BigDecimal.valueOf(10000))
                        .interestPayment(BigDecimal.valueOf(10000))
                        .debtPayment(BigDecimal.valueOf(10000))
                        .remainingDebt(BigDecimal.valueOf(10000)).build())).build(), id);

        Assertions.assertEquals(paymentScheduleRepository.count(), countPaymentScheduleRepository + 1);

        Application application = applicationServiceImpl.getApplication(id);

        Assertions.assertEquals(application.getId(), id);
        Assertions.assertEquals(application.getStatus(), Status.APPROVED);

    }
}