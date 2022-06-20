package com.neoflex.deal.services;

import com.neoflex.deal.dto.EmploymentDTO;
import com.neoflex.deal.dto.FinishRegistrationRequestDTO;
import com.neoflex.deal.dto.LoanApplicationRequestDTO;
import com.neoflex.deal.enums.*;
import com.neoflex.deal.models.*;
import com.neoflex.deal.repository.ApplicationRepository;
import com.neoflex.deal.repository.ApplicationStatusHistoryRepository;
import com.neoflex.deal.repository.PassportRepository;
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

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class DealServiceImplTest {

    @Autowired
    ApplicationServiceImpl applicationServiceImpl;
    @Autowired
    private ApplicationRepository applicationRepository;

    @InjectMocks
    private ApplicationStatusHistoryServiceImpl applicationStatusHistoryServiceImpl;

    @InjectMocks
    private DealServiceImpl dealServiceImpl;

    @BeforeEach
    void createReflection() {
        ReflectionTestUtils.setField(dealServiceImpl, "applicationServiceImpl", applicationServiceImpl);
        ReflectionTestUtils.setField(applicationServiceImpl, "applicationStatusHistoryServiceImpl", applicationStatusHistoryServiceImpl);
        ReflectionTestUtils.setField(applicationServiceImpl, "applicationRepository", applicationRepository);
        createApplication();
    }

    void createApplication() {
        applicationRepository.save(Application.builder()
                .creationDate(LocalDate.now())
                .status(Status.PREAPPROVAL)
                .client(Client.builder()
                        .birthdate(LocalDate.of(1980, 9, 26))
                        .email("usususu@mail.ru")
                        .firstName("Test")
                        .lastName("Test")
                        .middleName("Test")
                        .passport(Passport.builder()
                                .number("123456")
                                .series("1234")
                                .build())
                        .build())
                .credit(Credit.builder()
                        .amount(BigDecimal.valueOf(66000))
                        .creditStatus(CreditStatus.CALCULATED)
                        .monthlyPayment(BigDecimal.valueOf(3300))
                        .psk(BigDecimal.valueOf(66000))
                        .rate(BigDecimal.valueOf(20))
                        .term(20)
                        .addServices(AddServices.builder()
                                .isInsuranceEnabled(true)
                                .isSalaryClient(true)
                                .build())
                        .build())
                .build());
    }

    @Test
    void createScoringDataDTO() {
        Assertions.assertNotNull(dealServiceImpl.createScoringDataDTO(FinishRegistrationRequestDTO.builder()
                .genders(Genders.MALE)
                .maritalStatus(MaritalStatus.MARRIED)
                .dependentAmount(1)
                .passportIssueDate(LocalDate.of(2002, 9, 29))
                .passportIssueBrach("Улица Пушкина - Дом Колотушкина")
                .employment(EmploymentDTO.builder()
                        .employmentStatus(EmploymentStatus.SELF_EMPLOYED)
                        .employerINN("757838275")
                        .salary(BigDecimal.valueOf(10000))
                        .position(Position.TOP_MANAGER)
                        .workExperienceTotal(30)
                        .workExperienceCurrent(5)
                        .build())
                .account("40817810099910004312")
                .build(), applicationRepository.findTopByOrderByIdDesc().getId()));


    }
}